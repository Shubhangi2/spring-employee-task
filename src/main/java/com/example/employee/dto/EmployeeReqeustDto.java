package com.example.employee.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeReqeustDto {
    private String name;
    private String email;
    private String department;
    private String age;
    private String gender;
    private BigDecimal salary;
    private String joining_date;
    private String status;
    private String country;
    private String employeeId;
}




