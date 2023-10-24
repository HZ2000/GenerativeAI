package com.GenerativeAI.T3.service;

import com.GenerativeAI.T3.dto.doctor.CreateDoctorDTO;
import com.GenerativeAI.T3.dto.doctor.UpdateDoctorDTO;
import com.GenerativeAI.T3.model.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor getDoctorById(Long id);
    List<Doctor> getAllDoctors();
    Doctor createDoctor(CreateDoctorDTO doctorDTO);
    Doctor updateDoctorInfo(Long id, UpdateDoctorDTO doctorDTO);
    Doctor deleteDoctor(Long id);
}
