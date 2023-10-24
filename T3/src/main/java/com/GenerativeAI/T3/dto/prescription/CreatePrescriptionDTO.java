package com.GenerativeAI.T3.dto.prescription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePrescriptionDTO {
    @NotNull
    private Long appointmentId;
    @NotBlank
    private String medicationName;
    private String dosage;
    @NotBlank
    private String instructions;
}
