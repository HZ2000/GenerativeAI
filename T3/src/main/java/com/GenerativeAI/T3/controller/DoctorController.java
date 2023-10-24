package com.GenerativeAI.T3.controller;

import com.GenerativeAI.T3.dto.doctor.CreateDoctorDTO;
import com.GenerativeAI.T3.dto.doctor.UpdateDoctorDTO;
import com.GenerativeAI.T3.exception.DoctorNotFoundException;
import com.GenerativeAI.T3.model.Doctor;
import com.GenerativeAI.T3.service.DoctorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctor(@PathVariable @Min(1) Long id) {
        try {
            Doctor doctor = doctorService.getDoctorById(id);
            return ResponseEntity.ok(doctor);
        } catch (DoctorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody @Valid CreateDoctorDTO doctorDTO) {
        Doctor createdDoctor = doctorService.createDoctor(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable @Min(1) Long id, @RequestBody @Valid UpdateDoctorDTO doctorDTO) {
        try {
            Doctor updatedDoctor = doctorService.updateDoctorInfo(id, doctorDTO);
            return ResponseEntity.ok(updatedDoctor);
        } catch (DoctorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable @Min(1) Long id) {
        try {
            Doctor deletedDoctor = doctorService.deleteDoctor(id);
            return ResponseEntity.ok(deletedDoctor);
        } catch (DoctorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
