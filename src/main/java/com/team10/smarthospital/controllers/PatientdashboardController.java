package com.team10.smarthospital.controllers;
import com.team10.smarthospital.service.HospitalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class PatientdashboardController {

    @Autowired
    private HospitalDataService hospitalDataService;

    @GetMapping("/patient/patientDashboard")
    public String showPatientDashboard(Model model) {
        // sent data to front-end
        model.addAttribute("departments", hospitalDataService.getAllDepartments());
        model.addAttribute("availableTimes", hospitalDataService.getAvailableTimes());
        
        // src/main/resources/templates/patientdashboard.html
        return "patientdashboard";
    }
    
    // AJAX: choose department then loding doctor
    @GetMapping("/patient/getDoctors")
    @ResponseBody
    public List<String> getDoctorsByDepartment(@RequestParam String department) {
        return hospitalDataService.getDoctorsByDepartment(department);
    }
    
    // AJAX: choose department then loding nurse
    @GetMapping("/patient/getNurse")
    @ResponseBody
    public String getNurseByDepartment(@RequestParam String department) {
        return hospitalDataService.getNurseByDepartment(department);
    }
}