package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.FulltimeEmployee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Value("${app.data.mock-employee-path}")
    private String mockFilePath;
    // Service layer methods would go here
    // Service get all Employees to be called from the controller normally would call the persistance layer.
    // I'm assuming it would come from a json file. So I am read from the file and convert to list of Employees
    public List<Employee> getAllEmployees() {
        return readEmployeesFromFile().stream()
                .map(employee -> (Employee) employee) // casts the elements up to Employees
                .collect(Collectors.toList());
    }

    // read from the json file and find the employee with the matching uuid
    public Employee getEmployeeByUuid(UUID uuid) {
        return readEmployeesFromFile().stream()
                .filter(emp -> emp.getUuid().equals(uuid)) // checks for the uuid to match an employees in the "db"
                .findAny() // there should only be one as it is unique
                .orElse(null); // if no employee is found
    }

    // I am assuming the requestBody is in JSON format
    // and contains all the necessary fields to create an Employee object
    public Employee createEmployee(FulltimeEmployee newEmployee) {
        // If the employees UUID isnt given on create it will make one
        if (newEmployee.getUuid() == null) {
            newEmployee.setUuid(UUID.randomUUID());
        }

        // If the contract date isnt given on create its assumed to start now
        if (newEmployee.getContractHireDate() == null) {
            newEmployee.setContractHireDate(Instant.now());
        }

        // If the email isnt given assume it is FirstnameLastname@company.com
        if (newEmployee.getEmail() == null
                || newEmployee
                        .getEmail()
                        .trim()
                        .isEmpty()) { // checks if no email is given or if the email field is given but the value is an
            // empty string
            String firstName = newEmployee.getFirstName() != null ? newEmployee.getFirstName() : "";
            String lastName = newEmployee.getLastName() != null ? newEmployee.getLastName() : "";
            if (!firstName.isEmpty() || !lastName.isEmpty()) {
                newEmployee.setEmail(firstName + lastName + "@company.com");
            }
        }
        // add it to the json file
        List<FulltimeEmployee> allEmployees = readEmployeesFromFile();
        ObjectMapper objectMapper = new ObjectMapper();
        // I had to add it so it could handle the time
        objectMapper.findAndRegisterModules();
        try {
            allEmployees.add(newEmployee);
            // write the updated list back to the json file
            File jsonFile = new File(mockFilePath);
            objectMapper.writeValue(jsonFile, allEmployees);

            return newEmployee;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // read from the json file and convert to list of Employees
    private List<FulltimeEmployee> readEmployeesFromFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // I had to add it bc of the instant time values
            objectMapper.findAndRegisterModules();
            // lowkey had some trouble with the path (current working directory)
            File jsonFile = new File(mockFilePath);
            List<FulltimeEmployee> employees =
                    objectMapper.readValue(jsonFile, new TypeReference<List<FulltimeEmployee>>() {});
            return employees;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
