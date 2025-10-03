package com.challenge.api.model;

import java.time.Instant;
import java.util.UUID;

// fulltime ppl have health-insurance and other benefits not sure how to model that though
// full-time employee class
public class FulltimeEmployee implements Employee {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String fullName; // I am assuming full name is a combination of first and last
    private Integer salary;
    private Integer age;
    private String jobTitle;
    private String email;
    private Instant contractHireDate;
    private Instant contractTerminationDate; // nullable

    // empty constuctor
    public FulltimeEmployee() {}

    // I had constructors and then realized they were useless bc of Jackson.

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setFirstName(String name) {
        this.firstName = name;
        this.updateFullName();
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLastName(String name) {
        this.lastName = name;
        this.updateFullName();
    }

    @Override
    public String getFullName() {
        return this.fullName;
    }

    @Override
    public void setFullName(String name) {
        this.fullName = name;
    }

    @Override
    public Integer getSalary() {
        return this.salary;
    }

    @Override
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public Integer getAge() {
        return this.age;
    }

    @Override
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String getJobTitle() {
        return this.jobTitle;
    }

    @Override
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Instant getContractHireDate() {
        return this.contractHireDate;
    }

    @Override
    public void setContractHireDate(Instant date) {
        this.contractHireDate = date;
    }

    @Override
    public Instant getContractTerminationDate() {
        return this.contractTerminationDate;
    }

    @Override
    public void setContractTerminationDate(Instant date) {
        this.contractTerminationDate = date;
    }

    private void updateFullName() {
        String first = this.firstName != null ? this.firstName : "";
        String last = this.lastName != null ? this.lastName : "";
        this.fullName = (first + " " + last).trim();
    }
}
