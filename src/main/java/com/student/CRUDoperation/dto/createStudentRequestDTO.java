package com.student.CRUDoperation.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;

public class createStudentRequestDTO {
    @NotBlank(message = "Name cannot be null/empty or blank")
    @Size(min = 2 , max = 50,message = "Name must be within 2 to 50 character long")
    private String name;


    @Email
    @NotBlank (message = "Student email cannot be blank ")
    private String email;

    @Min(value=18,message = "Student must be atleast 18 years old ")
    @NotNull (message =" Age is Required")
    private  Integer age;

    @NotNull(message = "Roll number is required")
    @Positive(message = "Roll number must be greater than zero")
    private Integer rollNo;

    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
