package com.GenerativeAI.T3.controller;

import com.GenerativeAI.T3.dto.patient.CreatePatientDTO;
import com.GenerativeAI.T3.dto.patient.UpdatePatientDTO;
import com.GenerativeAI.T3.exception.PatientNotFoundException;
import com.GenerativeAI.T3.model.Patient;
import com.GenerativeAI.T3.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PatientControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPatient() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientService.getPatientById(patientId)).thenReturn(patient);

        ResponseEntity<?> response = patientController.getPatient(patientId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Patient);
    }

    @Test
    public void testGetPatientNotFound() {
        Long patientId = 1L;

        when(patientService.getPatientById(patientId)).thenThrow(PatientNotFoundException.class);

        ResponseEntity<?> response = patientController.getPatient(patientId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient());

        when(patientService.getAllPatients()).thenReturn(patients);

        List<Patient> result = patientController.getAllPatients();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testCreatePatient() {
        CreatePatientDTO createPatientDTO = new CreatePatientDTO();
        Patient createdPatient = new Patient();

        when(patientService.createPatient(createPatientDTO)).thenReturn(createdPatient);

        ResponseEntity<Patient> response = patientController.createPatient(createPatientDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdPatient, response.getBody());
    }

    @Test
    public void testUpdatePatient() {
        Long patientId = 1L;
        UpdatePatientDTO updatePatientDTO = new UpdatePatientDTO();
        Patient updatedPatient = new Patient();
        updatedPatient.setId(patientId);

        when(patientService.updatePatientInfo(patientId, updatePatientDTO)).thenReturn(updatedPatient);

        ResponseEntity<?> response = patientController.updatePatient(patientId, updatePatientDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Patient);
    }

    @Test
    public void testUpdatePatientNotFound() {
        Long patientId = 1L;
        UpdatePatientDTO updatePatientDTO = new UpdatePatientDTO();

        when(patientService.updatePatientInfo(patientId, updatePatientDTO)).thenThrow(PatientNotFoundException.class);

        ResponseEntity<?> response = patientController.updatePatient(patientId, updatePatientDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeletePatient() {
        Long patientId = 1L;
        Patient deletedPatient = new Patient();
        deletedPatient.setId(patientId);

        when(patientService.deletePatient(patientId)).thenReturn(deletedPatient);

        ResponseEntity<?> response = patientController.deletePatient(patientId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Patient);
    }

    @Test
    public void testDeletePatientNotFound() {
        Long patientId = 1L;

        when(patientService.deletePatient(patientId)).thenThrow(PatientNotFoundException.class);

        ResponseEntity<?> response = patientController.deletePatient(patientId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
