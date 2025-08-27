package com.team10.smarthospital.mapper;

import com.team10.smarthospital.model.entity.NurseVital;

import java.util.List;

public interface NurseVitalMapper {

    void insert(NurseVital nurseVital);

    List<NurseVital> getByAppointmentIds(List<String> appointmentIds);
}
