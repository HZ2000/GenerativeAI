package com.GenerativeAI.T3.dto;

import lombok.Data;

@Data
public class PrescriptionDTO {
    private String medicationName;
    private String dosage;
    private String instructions;
    private AppointmentDTO appointment;
}
