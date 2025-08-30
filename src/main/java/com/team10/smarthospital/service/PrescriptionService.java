package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.AppointmentMapper;
import com.team10.smarthospital.mapper.MedicationMapper;
import com.team10.smarthospital.mapper.PrescriptionMapper;
import com.team10.smarthospital.model.entity.Medication;
import com.team10.smarthospital.model.entity.Prescription;
import com.team10.smarthospital.model.enums.AppointmentStatus;
import com.team10.smarthospital.model.request.PrescriptionAdd;
import com.team10.smarthospital.model.response.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionService {

    @Autowired private PrescriptionMapper prescriptionMapper;

    @Autowired private MedicationMapper medicationMapper;

    @Autowired private AppointmentMapper appointmentMapper;

    @Transactional
    public BaseResponse<Void> addPrescription(PrescriptionAdd prescriptionAdd) {
        String appointmentId = prescriptionAdd.getAppointmentId();
        Prescription prescription = new Prescription();
        prescription.setAppointmentId(appointmentId);
        prescription.setDiagnosis(prescriptionAdd.getDiagnosis());
        prescription.setDrugAllergy(prescriptionAdd.getDrugAllergy());
        List<Medication> medications = prescriptionAdd.getMedications();
        Prescription prescriptionHistory = prescriptionMapper.getByAppointmentId(appointmentId);
        if (prescriptionHistory == null) {
            String prescriptionId = UUID.randomUUID().toString();

            prescriptionMapper.insert(prescription);
            medicationMapper.batchInsert(prescriptionId, medications);
            appointmentMapper.updateStatus(
                    appointmentId, AppointmentStatus.COMPLETED.getStatusCode());
        } else {
            String prescriptionId = prescriptionHistory.getPrescriptionId();
            prescription.setPrescriptionId(prescriptionId);
            prescriptionMapper.update(prescription);
            medicationMapper.deleteByPrescriptionId(prescriptionId);
            medicationMapper.batchInsert(prescriptionId, medications);
        }
        return BaseResponse.success(null);
    }
}
