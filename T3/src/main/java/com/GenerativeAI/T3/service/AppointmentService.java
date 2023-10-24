package com.GenerativeAI.T3.service;

import com.GenerativeAI.T3.dto.appointment.CreateAppointmentDTO;
import com.GenerativeAI.T3.dto.appointment.UpdateAppointmentDTO;
import com.GenerativeAI.T3.model.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment getAppointmentById(Long id);
    List<Appointment> getAllAppointmentsByPatientId(Long patientId);
    List<Appointment> getAllAppointmentsByDoctorId(Long doctorId);
    Appointment createAppointment(CreateAppointmentDTO createAppointmentDTO);
    Appointment updateAppointment(Long appointmentId, UpdateAppointmentDTO createAppointmentDTO);
    Appointment deleteAppointment(Long id);
}
