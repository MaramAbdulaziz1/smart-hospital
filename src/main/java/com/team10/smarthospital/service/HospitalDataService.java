package com.team10.smarthospital.service;

import com.team10.smarthospital.model.enums.EmployeeDepartment;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.Department;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalDataService {

    public BaseResponse<List<Department>> getAllDepartment() {
        List<Department> departments = new ArrayList<>();
        for (EmployeeDepartment employeeDepartment : EmployeeDepartment.values()) {
            Department department = new Department();
            department.setDepartmentCode(employeeDepartment.getDepartmentCode());
            department.setDepartmentName(employeeDepartment.getDepartmentName());
            departments.add(department);
        }
        return BaseResponse.success(null, departments);
    }
}
