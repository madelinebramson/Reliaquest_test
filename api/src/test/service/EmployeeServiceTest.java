package com.challenge.api.service;

import com.challenge.api.model.FulltimeEmployee;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleEmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    private final String DATA_FILE = "src/main/resources/test-employees.json";
    private final UUID EXISTING_UUID = UUID.fromString("1371ff20-0da5-4409-9cd3-bdf060fad9e9");
    private String originalContent;

    @BeforeAll
    void saveOriginalData() throws IOException {
        originalContent = Files.readString(Path.of(DATA_FILE));
    }

    @AfterEach
    void restoreDataFile() throws IOException {
        Files.writeString(Path.of(DATA_FILE), originalContent);
    }
    
    @Test
    void testGetAllEmployees() {
        assertEquals(1, employeeService.getAllEmployees().size());
    }

    @Test
    void testGetEmployeeByUuid_Found() {
        assertNotNull(employeeService.getEmployeeByUuid(EXISTING_UUID));
    }

    @Test
    void testCreateEmployee_SavesData() {
        FulltimeEmployee newEmp = new FulltimeEmployee();
        newEmp.setFirstName("Max");
        newEmp.setLastName("Well");
        
        employeeService.createEmployee(newEmp);

        assertEquals(2, employeeService.getAllEmployees().size());
    }
}