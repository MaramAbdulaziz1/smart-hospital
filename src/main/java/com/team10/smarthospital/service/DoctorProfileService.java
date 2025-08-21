package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.DoctorMapper;
import com.team10.smarthospital.model.entity.Doctor;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.DoctorDepartment;
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
        response.setGender(user.getGender());
        response.setEmail(user.getEmail());
        response.setRole(Role.DOCTOR.getRoleCode());
        DoctorDepartment department = DoctorDepartment.getDoctorDepartment(doctor.getDepartment());
        if (department != null) {
            response.setDepartment(department.getDepartmentCode());
            response.setDepartmentName(department.getDepartmentName());
        }
        response.setEmployeeId(doctor.getEmployeeId());
        response.setLocation(doctor.getLocation());
        response.setWorkNumber(doctor.getWorkNumber());
        return response;
    }
}
