package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.NurseMapper;
import com.team10.smarthospital.model.entity.Nurse;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.EmployeeDepartment;
import com.team10.smarthospital.model.enums.Gender;
import com.team10.smarthospital.model.enums.Role;
import com.team10.smarthospital.model.response.NurseProfileResponse;

import org.springframework.stereotype.Service;

@Service
public class NurseProfileService
        extends AbstractProfileService<Nurse, NurseProfileResponse, NurseMapper> {

    @Override
    protected NurseProfileResponse buildProfileResponse(User user, Nurse nurse) {
        NurseProfileResponse response = new NurseProfileResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setGenderName(Gender.getGenderName(user.getGender()));
        response.setEmail(user.getEmail());
        response.setRole(Role.NURSE.getRoleCode());
        response.setRoleName(Role.NURSE.getRoleName());
        response.setAvatarBase64(user.getAvatarBase64());
        response.setBirth(user.getBirth());
        response.setMobileNumber(user.getMobileNumber());
        response.setDepartmentName(
                EmployeeDepartment.getEmployeeDepartmentName(nurse.getDepartment()));
        response.setEmployeeId(nurse.getEmployeeId());
        response.setLocation(nurse.getLocation());
        response.setWorkNumber(nurse.getWorkNumber());
        return response;
    }
}
