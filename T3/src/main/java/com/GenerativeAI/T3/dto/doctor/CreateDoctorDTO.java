package com.GenerativeAI.T3.dto;

import lombok.Data;

import java.util.List;

@Data
public class DoctorDTO {
    private String name;
    private String specialization;
    private List<AppointmentDTO> appointments;
}
