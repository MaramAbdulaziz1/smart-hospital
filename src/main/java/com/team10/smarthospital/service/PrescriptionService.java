package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.MedicationMapper;
import com.team10.smarthospital.mapper.PrescriptionMapper;
import com.team10.smarthospital.model.entity.Medication;
import com.team10.smarthospital.model.entity.Prescription;
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

    @Transactional
    public BaseResponse<Void> addPrescription(PrescriptionAdd prescriptionAdd) {
        String appointmentId = prescriptionAdd.getAppointmentId();
        Prescription prescription = new Prescription();
        prescription.setAppointmentId(appointmentId);
        prescription.setDiagnosis(prescriptionAdd.getDiagnosis());
        prescription.setDrugAllergy(prescriptionAdd.getDrugAllergy());
        String prescriptionId = UUID.randomUUID().toString();
        prescription.setPrescriptionId(prescriptionId);
        prescriptionMapper.insert(prescription);
        List<Medication> medications = prescriptionAdd.getMedications();
        medicationMapper.batchInsert(prescriptionId, medications);
        return BaseResponse.success(null);
    }
}
