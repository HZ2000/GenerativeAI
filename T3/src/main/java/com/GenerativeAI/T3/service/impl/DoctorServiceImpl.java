package com.GenerativeAI.T3.service.impl;

import com.GenerativeAI.T3.dto.doctor.CreateDoctorDTO;
import com.GenerativeAI.T3.dto.doctor.UpdateDoctorDTO;
import com.GenerativeAI.T3.exception.DoctorNotFoundException;
import com.GenerativeAI.T3.model.Doctor;
import com.GenerativeAI.T3.repository.DoctorRepository;
import com.GenerativeAI.T3.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;

    @Override
    public Doctor getDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + id));
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor createDoctor(CreateDoctorDTO doctorDTO) {
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctorInfo(Long id, UpdateDoctorDTO doctorDTO) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isPresent()) {
            Doctor existingDoctor = doctor.get();
            modelMapper.map(doctorDTO, existingDoctor);
            existingDoctor.setId(id);
            existingDoctor = doctorRepository.save(existingDoctor);
            return existingDoctor;
        } else {
            throw new DoctorNotFoundException("Doctor not found with ID: " + id);
        }
    }

    @Override
    public Doctor deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new DoctorNotFoundException("Doctor not found with ID: " + id);
        }
        Doctor doctor = getDoctorById(id);
        doctorRepository.deleteById(id);
        return doctor;
    }
}
