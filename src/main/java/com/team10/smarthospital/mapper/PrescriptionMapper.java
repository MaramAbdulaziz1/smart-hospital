package com.team10.smarthospital.mapper;

import com.team10.smarthospital.model.entity.Prescription;

public interface PrescriptionMapper {
    void insert(Prescription prescription);

    Prescription getByAppointmentId(String appointmentId);

    void update(Prescription prescription);
}
