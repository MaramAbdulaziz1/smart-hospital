package com.team10.smarthospital.controllers;

import com.lowagie.text.DocumentException;
import com.team10.smarthospital.mapper.*;
import com.team10.smarthospital.model.entity.*;
import com.team10.smarthospital.model.enums.EmployeeDepartment;
import com.team10.smarthospital.model.response.VisitRecord;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientReportController {

    @Autowired private TemplateEngine templateEngine;

    @Autowired private PatientIntakeMapper patientIntakeMapper;
    @Autowired private AppointmentMapper appointmentMapper;
    @Autowired private PatientMapper patientMapper;
    @Autowired private DoctorMapper doctorMapper;
    @Autowired private PrescriptionMapper prescriptionMapper;
    @Autowired private MedicationMapper medicationMapper;
    @Autowired private UserMapper userMapper;

    @GetMapping("/patientReport")
    public String getPatientReport(
            Model model,
            @RequestParam(name = "intakeId") String intakeId,
            @RequestParam(name = "appointmentId") String appointmentId) {
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("intakeId", intakeId);
        PatientIntake patientIntake = patientIntakeMapper.getByIntakeId(intakeId);
        String thisAppointmentsById = patientIntake.getAppointmentId();
        Appointment appointment = appointmentMapper.getAppointmentsById(thisAppointmentsById);
        Patient patient = patientMapper.getUserByUserId(appointment.getPatientId());
        User patientUser = userMapper.getUserByUserId(appointment.getPatientId());
        User doctorUser = userMapper.getUserByUserId(appointment.getProviderId());
        Doctor doctor = doctorMapper.getUserByUserId(appointment.getProviderId());
        Prescription prescription = prescriptionMapper.getByAppointmentId(thisAppointmentsById);
        List<Medication> medications =
                medicationMapper.getByPrescriptionId(prescription.getPrescriptionId());
        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setPatientCode(patient.getPatientCode());
        visitRecord.setPatientName(patientUser.getFullName());
        visitRecord.setDateOfVisit(appointment.getDate());
        visitRecord.setDepartment(
                EmployeeDepartment.getEmployeeDepartmentName(doctor.getDepartment()));
        visitRecord.setDiagnosis(patientIntake.getDiagnosis());
        visitRecord.setDoctorName(doctorUser.getFullName());
        visitRecord.setDoctorsNotes(patientIntake.getNotes());
        visitRecord.setTreatmentPlan(patientIntake.getFollowUpPlan());
        visitRecord.setAllergies(patient.getAllergies());
        List<String> prescriptions = new ArrayList<>();
        if (!medications.isEmpty()) {
            for (Medication m : medications) {
                prescriptions.add(m.getName() + " " + m.getDosage());
            }
        }
        visitRecord.setCurrentMedications(prescriptions);
        visitRecord.setFamilyHistory(patient.getFamilyHistory());
        visitRecord.setSocialHistory(patient.getSocialHistory());
        visitRecord.setPastSurgicalHistory(patient.getPastSurgicalHistory());
        visitRecord.setPastMedications(patient.getPastMedications());
        visitRecord.setPastMedicalConditions(patient.getPastMedicalConditions());

        model.addAttribute("visitRecord", visitRecord);
        model.addAttribute("pageTitle", "Patient Report");
        return "patient-report";
    }

    @GetMapping("/downloadPatientReport")
    public void downloadPatientReportAsPdf(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(name = "intakeId") String intakeId,
            @RequestParam(name = "appointmentId") String appointmentId)
            throws IOException, DocumentException {

        response.setContentType("application/pdf");
        String fileName = "patient-report-" + intakeId + "-" + appointmentId + ".pdf";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        ModelAndView modelAndView = new ModelAndView("patient-report");
        modelAndView.addObject("appointmentId", appointmentId);
        modelAndView.addObject("intakeId", intakeId);
        modelAndView.addObject("pageTitle", "Patient Report");

        String htmlContent = "upcoming";

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        renderer.createPDF(response.getOutputStream());
    }
}
