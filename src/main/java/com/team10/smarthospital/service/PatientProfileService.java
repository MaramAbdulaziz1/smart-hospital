package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.PatientMapper;
import com.team10.smarthospital.model.entity.Patient;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.BloodType;
import com.team10.smarthospital.model.enums.Gender;
import com.team10.smarthospital.model.enums.Relationship;
import com.team10.smarthospital.model.enums.Role;
import com.team10.smarthospital.model.request.PatientProfileRequest;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.PatientProfileResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientProfileService
        extends AbstractProfileService<Patient, PatientProfileResponse, PatientMapper> {

    @Autowired private PatientService patientService;

    @Override
    protected PatientProfileResponse buildProfileResponse(User user, Patient patient) {
        PatientProfileResponse response = new PatientProfileResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setGenderName(Gender.getGenderName(user.getGender()));
        response.setEmail(user.getEmail());
        response.setRole(Role.PATIENT.getRoleCode());
        response.setRoleName(Role.PATIENT.getRoleName());
        response.setAvatarBase64(user.getAvatarBase64());
        response.setBirth(user.getBirth());
        response.setMobileNumber(user.getMobileNumber());
        response.setPatientCode(patient.getPatientCode());
        response.setHeight(patient.getHeight());
        response.setWeight(patient.getWeight());
        response.setBloodTypeName(BloodType.getBloodTypeName(patient.getBloodType()));
        response.setAddress(patient.getAddress());
        response.setNationalId(patient.getNationalId());
        response.setNearestClinic(patient.getNearestClinic());
        response.setEcFirstName(patient.getEcFirstName());
        response.setEcLastName(patient.getEcLastName());
        response.setEcRelationshipName(
                Relationship.getRelationshipName(patient.getEcRelationship()));
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

    public BaseResponse<Void> updatePatientProfile(
            String email, PatientProfileRequest patientProfileRequest) {
        return patientService.updatePatientProfile(email, patientProfileRequest);
    }
}
