package com.GenerativeAI.T3.dto.doctor;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDoctorDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String specialization;
}
