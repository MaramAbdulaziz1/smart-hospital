package com.team10.smarthospital.mapper;

import com.team10.smarthospital.model.entity.Medication;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MedicationMapper {

    void batchInsert(
            @Param("prescriptionId") String prescriptionId,
            @Param("medications") List<Medication> medications);
}
