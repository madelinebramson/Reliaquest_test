package com.challenge.api.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String DATA_FILE = "src/main/resources/test-employees.json";
    private final String UUID = "1371ff20-0da5-4409-9cd3-bdf060fad9e9";
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
    void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetEmployeeByUuid() throws Exception {
        mockMvc.perform(get("/api/employees/{uuid}", UUID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(UUID));
    }

    @Test
    void testCreateEmployee() throws Exception {
        String json = "{\"firstName\":\"Max\",\"lastName\":\"Well\",\"salary\":60000}";

        mockMvc.perform(post("/api/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("MaxWell@company.com"));
        
        mockMvc.perform(get("/api/employees"))
                .andExpect(jsonPath("$.length()").value(2)); 
    }
}