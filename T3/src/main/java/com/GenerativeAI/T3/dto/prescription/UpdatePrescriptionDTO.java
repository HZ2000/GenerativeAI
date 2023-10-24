package com.GenerativeAI.T3.dto.prescription;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePrescriptionDTO {
    @NotNull
    private Long appointmentId;
    private String medicationName;
    private String dosage;
    private String instructions;
}
