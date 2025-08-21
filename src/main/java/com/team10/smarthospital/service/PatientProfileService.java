package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.PatientMapper;
import com.team10.smarthospital.model.entity.Patient;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.Role;
import com.team10.smarthospital.model.response.PatientProfileResponse;

import org.springframework.stereotype.Service;

@Service
public class PatientProfileService
        extends AbstractProfileService<Patient, PatientProfileResponse, PatientMapper> {

    @Override
    protected PatientProfileResponse buildProfileResponse(User user, Patient patient) {
        PatientProfileResponse response = new PatientProfileResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setGender(user.getGender());
        response.setEmail(user.getEmail());
        response.setRole(Role.PATIENT.getRoleCode());
        response.setAvatarBase64(user.getAvatarBase64());
        response.setBirth(user.getBirth());
        response.setMobileNumber(user.getMobileNumber());
        response.setPatientCode(patient.getPatientCode());
        response.setBirth(patient.getBirth());
        response.setHeight(patient.getHeight());
        response.setWeight(patient.getWeight());
        response.setBloodType(patient.getBloodType());
        response.setAddress(patient.getAddress());
        response.setNationalId(patient.getNationalId());
        response.setNearestClinic(patient.getNearestClinic());
        response.setEcFirstName(patient.getEcFirstName());
        response.setEcLastName(patient.getEcLastName());
        response.setEcRelationship(patient.getEcRelationship());
        response.setEcMobileNumber(patient.getEcMobileNumber());
        response.setChiefComplaint(patient.getChiefComplaint());
        response.setAllergies(patient.getAllergies());
        response.setPastMedicalConditions(patient.getPastMedicalConditions());
        response.setCurrentMedications(patient.getCurrentMedications());
        response.setPastSurgicalHistory(patient.getPastSurgicalHistory());
        response.setPastMedications(patient.getPastMedications());
        response.setFamilyHistory(patient.getFamilyHistory());
        response.setSocialHistory(patient.getSocialHistory());
        return response;
    }
}
