package com.example.employee.service;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.dto.EmployeeReqeustDto;
import com.example.employee.entity.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeReqeustDto employeeRequestDto) ;
    List<Employee> uploadEmployeeData(MultipartFile excelFile);
}
