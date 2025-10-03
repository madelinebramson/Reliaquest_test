package com.challenge.api.model;

import com.challenge.api.model.FulltimeEmployee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions;

import java.beans.Transient;

public class FulltimeEmployeeTest {
    @Test
    void testSettersAndGetters(){
        FulltimeEmployee employee = new FulltimeEmployee();
        UUID id = UUID.randomUUID();
        employee.setUuid(id);
        employee.setFirstName("Jane");
        employee.setLastName("Doe");
        employee.setSalary(65000);
        employee.setAge(27);
        employee.setJobTitle("Engineer");
        employee.setEmail("JaneDoe@company.com");
        Instant hireDate = Instant.now();
        employee.setContractHireDate(hireDate);
        Instant terminationDate = Instant.now();
        employee.setContractTerminationDate(terminationDate);

        assertEquals(id, employee.getUuid());
        assertEquals("Jane", employee.getFirstName());
        assertEquals("Doe", employee.getLastNmae());
        assertEquals(65000, employee.getSalary());
        assertEquals(27, employee.getAge());
        assertEquals("Engineer", employee.getJobTitle());
        assertEquals("JaneDoe@company.com", employee.getEmail());
        assertEquals(hireDate, employee.getContractHireDate());
        assertEquals(terminationDate, employee.setContractTerminationDate());
    }

    @Test
    void testFullName(){
        FulltimeEmployee employee = new  FulltimeEmployee();
        employee.setFirstName("John");
        employee.setLastName("Doe");

        assertEquals("John Doe", employee.getFullName);
    }

}
