package com.example.employee.service.impl;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.dto.EmployeeReqeustDto;
import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    final EmployeeRepository employeeRepository;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }



    @Override
    public EmployeeDto createEmployee(EmployeeReqeustDto employeeRequestDto) {
        Employee employee = new Employee(
                employeeRequestDto.getName(),
                employeeRequestDto.getEmail(),
                employeeRequestDto.getDepartment(),
                employeeRequestDto.getAge(),
                employeeRequestDto.getGender(),
                employeeRequestDto.getSalary(),
                employeeRequestDto.getJoining_date(),
                employeeRequestDto.getStatus(),
                employeeRequestDto.getCountry(),
                employeeRequestDto.getEmployeeId()
        );

      Employee employeeResponse =  employeeRepository.save(employee);
      return convertToDto(employeeResponse);
    }

    @Override
    public List<Employee> uploadEmployeeData(MultipartFile excelFile) {
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        if(!excelFile.isEmpty()){
            ByteArrayInputStream stream;
            Workbook wb;
            StringBuilder contentWb = new StringBuilder();
            Employee employeeResponse;

            try{
                stream = new ByteArrayInputStream(excelFile.getBytes());
                wb = WorkbookFactory.create(stream);
                Sheet sheet = wb.getSheetAt(wb.getActiveSheetIndex());

                System.out.println("Processing excel file");
                for(int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++){
                    System.out.println("rowIndex for loop "+ rowIndex);
                    Row row = sheet.getRow(rowIndex);
                    Employee employee = new Employee();
                    employee.setEmployee_id(row.getCell(0).getStringCellValue());
                    System.out.println("row.getCell(0).getStringCellValue() : "+ row.getCell(0).getStringCellValue());
                    employee.setName(getCellValueAsString(row.getCell(1)));
                    employee.setEmail(getCellValueAsString(row.getCell(2)));
                    employee.setDepartment(getCellValueAsString(row.getCell(3)));
                    employee.setAge(getCellValueAsString(row.getCell(4)));
                    employee.setGender(getCellValueAsString(row.getCell(5)));
                    employee.setSalary(BigDecimal.valueOf(Integer.parseInt(getCellValueAsString(row.getCell(6)))));
                    employee.setJoining_date(getCellValueAsString(row.getCell(7)));
                    employee.setStatus(getCellValueAsString(row.getCell(8)));
                    employee.setCountry(getCellValueAsString(row.getCell(9)));
                    employeeResponse =  employeeRepository.save(employee);
                    employeeArrayList.add(employeeResponse);
                    System.out.println("Employee value : ");
                    System.out.println(employeeResponse.toString());
                }

            return employeeArrayList;
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return employeeArrayList;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Handle date cells
                    return cell.getDateCellValue().toString();
                } else {
                    // Handle numeric cells - remove decimal point for whole numbers
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                // Evaluate formula and get the result
                return getCellValueAsString(cell.getCachedFormulaResultType(), cell);

            case BLANK:
                return "";

            default:
                return "";
        }
    }

    private String getCellValueAsString(CellType cellType, Cell cell) {
        switch (cellType) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                double numericValue = cell.getNumericCellValue();
                if (numericValue == (long) numericValue) {
                    return String.valueOf((long) numericValue);
                } else {
                    return String.valueOf(numericValue);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }


    EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setDepartment(employee.getDepartment());
        employeeDto.setAge(employee.getAge());
        employeeDto.setGender(employee.getGender());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setJoining_date(employee.getJoining_date());
        employeeDto.setStatus(employee.getStatus());
        employeeDto.setCountry(employee.getCountry());
        return employeeDto;
    }


}
