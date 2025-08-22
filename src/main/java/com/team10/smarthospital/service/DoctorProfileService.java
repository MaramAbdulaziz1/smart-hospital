package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.DoctorMapper;
import com.team10.smarthospital.model.entity.Doctor;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.EmployeeDepartment;
import com.team10.smarthospital.model.enums.Gender;
import com.team10.smarthospital.model.enums.Role;
import com.team10.smarthospital.model.response.DoctorProfileResponse;

import org.springframework.stereotype.Service;

@Service
public class DoctorProfileService
        extends AbstractProfileService<Doctor, DoctorProfileResponse, DoctorMapper> {

    @Override
    protected DoctorProfileResponse buildProfileResponse(User user, Doctor doctor) {
        DoctorProfileResponse response = new DoctorProfileResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setGenderName(Gender.getGenderName(user.getGender()));
        response.setEmail(user.getEmail());
        response.setRole(Role.DOCTOR.getRoleCode());
        response.setRoleName(Role.DOCTOR.getRoleName());
        response.setAvatarBase64(user.getAvatarBase64());
        response.setBirth(user.getBirth());
        response.setMobileNumber(user.getMobileNumber());
        response.setDepartmentName(
                EmployeeDepartment.getEmployeeDepartmentName(doctor.getDepartment()));
        response.setEmployeeId(doctor.getEmployeeId());
        response.setLocation(doctor.getLocation());
        response.setWorkNumber(doctor.getWorkNumber());
        return response;
    }
}
