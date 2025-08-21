package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.NurseMapper;
import com.team10.smarthospital.model.entity.Nurse;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.DoctorDepartment;
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
        response.setGender(user.getGender());
        response.setEmail(user.getEmail());
        response.setRole(Role.NURSE.getRoleCode());
        DoctorDepartment department = DoctorDepartment.getDoctorDepartment(nurse.getDepartment());
        if (department != null) {
            response.setDepartment(department.getDepartmentCode());
            response.setDepartmentName(department.getDepartmentName());
        }
        response.setEmployeeId(nurse.getEmployeeId());
        response.setLocation(nurse.getLocation());
        response.setWorkNumber(nurse.getWorkNumber());
        return response;
    }
}
