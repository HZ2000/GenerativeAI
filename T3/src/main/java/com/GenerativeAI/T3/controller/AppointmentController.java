package com.GenerativeAI.T3.controller;

import com.GenerativeAI.T3.dto.appointment.CreateAppointmentDTO;
import com.GenerativeAI.T3.dto.appointment.UpdateAppointmentDTO;
import com.GenerativeAI.T3.exception.AppointmentNotFoundException;
import com.GenerativeAI.T3.exception.DoctorNotFoundException;
import com.GenerativeAI.T3.exception.PatientNotFoundException;
import com.GenerativeAI.T3.model.Appointment;
import com.GenerativeAI.T3.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointment(@PathVariable @Min(1) Long id) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(appointment);
        } catch (AppointmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getAppointmentsByPatient(@PathVariable @Min(1) Long patientId) {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointmentsByPatientId(patientId);
            return ResponseEntity.ok(appointments);
        } catch (AppointmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getAppointmentsByDoctor(@PathVariable @Min(1) Long doctorId) {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointmentsByDoctorId(doctorId);
            return ResponseEntity.ok(appointments);
        } catch (AppointmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody @Valid CreateAppointmentDTO createAppointmentDTO) {
        try {
            Appointment createdAppointment = appointmentService.createAppointment(createAppointmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
        } catch (PatientNotFoundException | DoctorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable @Min(1)  Long id, @RequestBody @Valid UpdateAppointmentDTO updateAppointmentDTO) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, updateAppointmentDTO);
            return ResponseEntity.ok(updatedAppointment);
        } catch (AppointmentNotFoundException | PatientNotFoundException | DoctorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable @Min(1) Long id) {
        try {
            Appointment deletedAppointment = appointmentService.deleteAppointment(id);
            return ResponseEntity.ok(deletedAppointment);
        } catch (AppointmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
