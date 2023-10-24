package com.GenerativeAI.T3.controller;

import com.GenerativeAI.T3.dto.prescription.CreatePrescriptionDTO;
import com.GenerativeAI.T3.dto.prescription.UpdatePrescriptionDTO;
import com.GenerativeAI.T3.exception.AppointmentNotFoundException;
import com.GenerativeAI.T3.exception.PrescriptionNotFoundException;
import com.GenerativeAI.T3.model.Prescription;
import com.GenerativeAI.T3.service.PrescriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrescription(@PathVariable @Min(1) Long id) {
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            return ResponseEntity.ok(prescription);
        } catch (PrescriptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getPrescriptionsByPatient(@PathVariable @Min(1) Long patientId) {
        try {
            List<Prescription> prescriptions = prescriptionService.getAllPrescriptionsByPatientId(patientId);
            return ResponseEntity.ok(prescriptions);
        } catch (PrescriptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getPrescriptionsByDoctor(@PathVariable @Min(1) Long doctorId) {
        try {
            List<Prescription> prescriptions = prescriptionService.getAllPrescriptionsByDoctorId(doctorId);
            return ResponseEntity.ok(prescriptions);
        } catch (PrescriptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> getPrescriptionsByAppointment(@PathVariable @Min(1) Long appointmentId) {
        try {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByAppointmentId(appointmentId);
            return ResponseEntity.ok(prescriptions);
        } catch (PrescriptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createPrescription(@RequestBody @Valid CreatePrescriptionDTO createPrescriptionDTO) {
        try {
            Prescription createdPrescription = prescriptionService.createPrescription(createPrescriptionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPrescription);
        } catch (AppointmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrescription(@PathVariable @Min(1) Long id, @RequestBody @Valid UpdatePrescriptionDTO updatePrescriptionDTO) {
        try {
            Prescription updatedPrescription = prescriptionService.updatePrescription(id, updatePrescriptionDTO);
            return ResponseEntity.ok(updatedPrescription);
        } catch (PrescriptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrescription(@PathVariable @Min(1) Long id) {
        try {
            Prescription deletedPrescription = prescriptionService.deletePrescription(id);
            return ResponseEntity.ok(deletedPrescription);
        } catch (PrescriptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
