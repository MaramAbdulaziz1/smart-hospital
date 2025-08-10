package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.Gender;
import com.team10.smarthospital.model.Patient;
import com.team10.smarthospital.model.VisitRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientController {

  @GetMapping("/patientProfile")
  public String patientProfilePage(Model model) {
    // create mock patient data
    Patient patient = new Patient();
    patient.setName("Harry Potter");
    patient.setEmail("harrypotter2000@gmail.com");
    patient.setPhone("+44 07458545663");
    patient.setSex(Gender.MALE);
    patient.setAge(30);
    patient.setDateOfBirth(LocalDate.of(1999, 8, 23));
    patient.setWeight("75 kg");
    patient.setHeight("178 cm");
    patient.setBloodType("A");
    patient.setAddress("Marlborough House, Bristol, BS1 3NX");

    // create mock visit history
    List<VisitRecord> history = new ArrayList<>();
    history.add(new VisitRecord("10 Jul 2025", "General Medicine", "Viral Flu", "Dr. Sarah Moyo"));
    history.add(new VisitRecord("18 Jun 2024", "Dermatology", "Eczema", "Dr. Samuel Chen"));
    history.add(new VisitRecord("02 May 2024", "Cardiology", "Hypertension", "Dr. Linda Patel"));
    history.add(new VisitRecord("27 Apr 2023", "Neurology", "Migraine", "Dr. Emily Zhou"));

    // add patient and history into model
    model.addAttribute("patient", patient);
    model.addAttribute("history", history);

    return "patientProfile";
  }
}
