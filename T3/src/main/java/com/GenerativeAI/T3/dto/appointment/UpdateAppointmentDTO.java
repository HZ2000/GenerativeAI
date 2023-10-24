package com.GenerativeAI.T3.dto.appointment;

import com.GenerativeAI.T3.dto.prescription.CreatePrescriptionDTO;
import com.GenerativeAI.T3.model.Doctor;
import com.GenerativeAI.T3.model.Patient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateAppointmentDTO {
    @NotNull
    private Patient patient;
    @NotNull
    private Doctor doctor;
    private LocalDateTime appointmentDateTime;
    private List<CreatePrescriptionDTO> prescriptions;
}
