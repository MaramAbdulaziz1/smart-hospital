package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.Patient;
import com.team10.smarthospital.model.VisitRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientController {

  @GetMapping("/patientProfile")
  public String patientProfilePage(Model model) {
    // 建立假病人資料
    Patient patient = new Patient();

    // 建立假病歷紀錄
    List<VisitRecord> history = new ArrayList<>();
    history.add(new VisitRecord("10 Jul 2025", "General Medicine", "Viral Flu", "Dr. Sarah Moyo"));
    history.add(new VisitRecord("18 Jun 2024", "Dermatology", "Eczema", "Dr. Samuel Chen"));
    history.add(new VisitRecord("02 May 2024", "Cardiology", "Hypertension", "Dr. Linda Patel"));
    history.add(new VisitRecord("27 Apr 2023", "Neurology", "Migraine", "Dr. Emily Zhou"));

    // 將病人與歷史資料加入 model，傳給 HTML
    model.addAttribute("patient", patient);
    model.addAttribute("history", history);

    // 返回 Thymeleaf 頁面（patientProfile.html）
    return "patientProfile";
  }
}
