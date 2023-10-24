package com.GenerativeAI.T3.service;

import com.GenerativeAI.T3.dto.patient.CreatePatientDTO;
import com.GenerativeAI.T3.dto.patient.UpdatePatientDTO;
import com.GenerativeAI.T3.model.Patient;

import java.util.List;

public interface PatientService {
    Patient getPatientById(Long id);
    List<Patient> getAllPatients();
    Patient createPatient(CreatePatientDTO patientDTO);
    Patient updatePatientInfo(Long id, UpdatePatientDTO patientDTO);
    Patient deletePatient(Long id);
}
