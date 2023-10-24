package com.GenerativeAI.T3.dto;

import com.GenerativeAI.T3.dto.doctor.CreateDoctorDTO;
import com.GenerativeAI.T3.dto.patient.CreatePatientDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AppointmentDTO {
    private LocalDateTime appointmentDateTime;
    private CreatePatientDTO patient;
    private CreateDoctorDTO doctor;
    private List<PrescriptionDTO> prescriptions;
}
