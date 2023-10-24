package com.GenerativeAI.T3.controller;

import com.GenerativeAI.T3.dto.patient.CreatePatientDTO;
import com.GenerativeAI.T3.dto.patient.UpdatePatientDTO;
import com.GenerativeAI.T3.exception.PatientNotFoundException;
import com.GenerativeAI.T3.model.Patient;
import com.GenerativeAI.T3.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable @Min(1) Long id) {
        try {
            Patient patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        } catch (PatientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody @Valid CreatePatientDTO patientDTO) {
        Patient createdPatient = patientService.createPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable @Min(1) Long id, @RequestBody @Valid UpdatePatientDTO patientDTO) {
        try {
            Patient updatedPatient = patientService.updatePatientInfo(id, patientDTO);
            return ResponseEntity.ok(updatedPatient);
        } catch (PatientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        try {
            Patient deletedPatient = patientService.deletePatient(id);
            return ResponseEntity.ok(deletedPatient);
        } catch (PatientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
