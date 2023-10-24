package com.GenerativeAI.T3.dto.patient;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdatePatientDTO {
    private String name;
    @Email
    private String email;
    private int age;
    private String phoneNumber;
    private String address;
}
