package com.GenerativeAI.T3.service.impl;

import com.GenerativeAI.T3.dto.patient.CreatePatientDTO;
import com.GenerativeAI.T3.dto.patient.UpdatePatientDTO;
import com.GenerativeAI.T3.exception.PatientNotFoundException;
import com.GenerativeAI.T3.model.Patient;
import com.GenerativeAI.T3.repository.PatientRepository;
import com.GenerativeAI.T3.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;

    @Override
    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient createPatient(CreatePatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatientInfo(Long id, UpdatePatientDTO patientDTO) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            Patient existingPatient = patient.get();
            modelMapper.map(patientDTO, existingPatient);
            existingPatient.setId(id);
            existingPatient = patientRepository.save(existingPatient);
            return existingPatient;
        } else {
            throw new PatientNotFoundException("Patient not found with ID: " + id);
        }
    }

    @Override
    public Patient deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with ID: " + id);
        }
        Patient patient = getPatientById(id);
        patientRepository.deleteById(id);
        return patient;
    }
}
