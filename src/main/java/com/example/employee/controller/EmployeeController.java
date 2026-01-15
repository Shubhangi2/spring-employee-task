package com.example.employee.controller;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.dto.EmployeeReqeustDto;
import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/createEmployee")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeReqeustDto requestBody) {
        return ResponseEntity.ok(employeeService.createEmployee(requestBody));
    }

    @PostMapping("/uploadData")
    public ResponseEntity<List<Employee>> uploadExcelData(@RequestPart(value = "excel") MultipartFile excelFile){
        System.out.println("excel heading");
        System.out.println(excelFile.getName());
        System.out.println(excelFile.getOriginalFilename());
        System.out.println(excelFile.getSize());
        return ResponseEntity.ok(employeeService.uploadEmployeeData(excelFile));
    }
}
