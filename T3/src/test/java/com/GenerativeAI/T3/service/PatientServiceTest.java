package com.GenerativeAI.T3.service;

import com.GenerativeAI.T3.config.TestConfig;
import com.GenerativeAI.T3.dto.patient.CreatePatientDTO;
import com.GenerativeAI.T3.dto.patient.UpdatePatientDTO;
import com.GenerativeAI.T3.exception.PatientNotFoundException;
import com.GenerativeAI.T3.model.Patient;
import com.GenerativeAI.T3.repository.PatientRepository;
import com.GenerativeAI.T3.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = TestConfig.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPatientById() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById(patientId);

        assertNotNull(result);
        assertEquals(patientId, result.getId());
    }

    @Test
    public void testGetPatientByIdNotFound() {
        Long patientId = 1L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> {
            patientService.getPatientById(patientId);
        });
    }

    @Test
    public void testGetAllPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient());
        patients.add(new Patient());

        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.getAllPatients();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testCreatePatient() {
        CreatePatientDTO patientDTO = new CreatePatientDTO();
        Patient patient = new Patient();

        when(modelMapper.map(patientDTO, Patient.class)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);

        Patient result = patientService.createPatient(patientDTO);

        assertNotNull(result);
    }

    @Test
    public void testUpdatePatientInfo() {
        Long patientId = 1L;
        UpdatePatientDTO patientDTO = new UpdatePatientDTO();
        patientDTO.setName("Updated Name");
        Patient existingPatient = new Patient();
        existingPatient.setId(patientId);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(existingPatient)).thenReturn(existingPatient);

        Patient result = patientService.updatePatientInfo(patientId, patientDTO);

        assertNotNull(result);
        assertEquals(patientId, result.getId());
    }

    @Test
    public void testUpdatePatientInfoNotFound() {
        Long patientId = 1L;
        UpdatePatientDTO patientDTO = new UpdatePatientDTO();

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> {
            patientService.updatePatientInfo(patientId, patientDTO);
        });
    }

    @Test
    public void testDeletePatient() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientRepository.existsById(patientId)).thenReturn(true);
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        Patient result = patientService.deletePatient(patientId);

        assertNotNull(result);
        assertEquals(patientId, result.getId());
    }

    @Test
    public void testDeletePatientNotFound() {
        Long patientId = 1L;

        when(patientRepository.existsById(patientId)).thenReturn(false);

        assertThrows(PatientNotFoundException.class, () -> {
            patientService.deletePatient(patientId);
        });
    }
}
