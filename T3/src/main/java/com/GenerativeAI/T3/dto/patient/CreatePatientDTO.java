package com.GenerativeAI.T3.dto.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePatientDTO {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private int age;
    private String phoneNumber;
    private String address;
}
