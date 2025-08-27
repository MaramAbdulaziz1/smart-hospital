package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.request.PrescriptionAdd;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.service.PrescriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired private PrescriptionService prescriptionService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('DOCTOR')")
    public BaseResponse<Void> addPrescription(@RequestBody PrescriptionAdd prescriptionAdd) {
        return prescriptionService.addPrescription(prescriptionAdd);
    }
}
