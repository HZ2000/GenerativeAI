package com.GenerativeAI.T3.controller;

import com.GenerativeAI.T3.dto.prescription.CreatePrescriptionDTO;
import com.GenerativeAI.T3.dto.prescription.UpdatePrescriptionDTO;
import com.GenerativeAI.T3.exception.AppointmentNotFoundException;
import com.GenerativeAI.T3.exception.PrescriptionNotFoundException;
import com.GenerativeAI.T3.model.Prescription;
import com.GenerativeAI.T3.service.PrescriptionService;
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

public class PrescriptionControllerTest {

    @InjectMocks
    private PrescriptionController prescriptionController;

    @Mock
    private PrescriptionService prescriptionService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPrescription() {
        Long prescriptionId = 1L;
        Prescription prescription = new Prescription();
        prescription.setId(prescriptionId);

        when(prescriptionService.getPrescriptionById(prescriptionId)).thenReturn(prescription);

        ResponseEntity<?> response = prescriptionController.getPrescription(prescriptionId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Prescription);
    }

    @Test
    public void testGetPrescriptionNotFound() {
        Long prescriptionId = 1L;

        when(prescriptionService.getPrescriptionById(prescriptionId)).thenThrow(PrescriptionNotFoundException.class);

        ResponseEntity<?> response = prescriptionController.getPrescription(prescriptionId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetPrescriptionsByPatient() {
        Long patientId = 1L;
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(new Prescription());

        when(prescriptionService.getAllPrescriptionsByPatientId(patientId)).thenReturn(prescriptions);

        ResponseEntity<?> response = prescriptionController.getPrescriptionsByPatient(patientId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    public void testGetPrescriptionsByPatientNotFound() {
        Long patientId = 1L;

        when(prescriptionService.getAllPrescriptionsByPatientId(patientId)).thenThrow(PrescriptionNotFoundException.class);

        ResponseEntity<?> response = prescriptionController.getPrescriptionsByPatient(patientId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetPrescriptionsByDoctor() {
        Long doctorId = 1L;
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(new Prescription());

        when(prescriptionService.getAllPrescriptionsByDoctorId(doctorId)).thenReturn(prescriptions);

        ResponseEntity<?> response = prescriptionController.getPrescriptionsByDoctor(doctorId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    public void testGetPrescriptionsByDoctorNotFound() {
        Long doctorId = 1L;

        when(prescriptionService.getAllPrescriptionsByDoctorId(doctorId)).thenThrow(PrescriptionNotFoundException.class);

        ResponseEntity<?> response = prescriptionController.getPrescriptionsByDoctor(doctorId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetPrescriptionsByAppointment() {
        Long appointmentId = 1L;
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(new Prescription());

        when(prescriptionService.getPrescriptionsByAppointmentId(appointmentId)).thenReturn(prescriptions);

        ResponseEntity<?> response = prescriptionController.getPrescriptionsByAppointment(appointmentId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    public void testGetPrescriptionsByAppointmentNotFound() {
        Long appointmentId = 1L;

        when(prescriptionService.getPrescriptionsByAppointmentId(appointmentId)).thenThrow(PrescriptionNotFoundException.class);

        ResponseEntity<?> response = prescriptionController.getPrescriptionsByAppointment(appointmentId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreatePrescription() {
        CreatePrescriptionDTO createPrescriptionDTO = new CreatePrescriptionDTO();
        Prescription createdPrescription = new Prescription();

        when(prescriptionService.createPrescription(createPrescriptionDTO)).thenReturn(createdPrescription);

        ResponseEntity<?> response = prescriptionController.createPrescription(createPrescriptionDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Prescription);
    }

    @Test
    public void testCreatePrescriptionWithAppointmentNotFound() {
        CreatePrescriptionDTO createPrescriptionDTO = new CreatePrescriptionDTO();

        when(prescriptionService.createPrescription(createPrescriptionDTO)).thenThrow(AppointmentNotFoundException.class);

        ResponseEntity<?> response = prescriptionController.createPrescription(createPrescriptionDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdatePrescription() {
        Long prescriptionId = 1L;
        UpdatePrescriptionDTO updatePrescriptionDTO = new UpdatePrescriptionDTO();
        Prescription updatedPrescription = new Prescription();
        updatedPrescription.setId(prescriptionId);

        when(prescriptionService.updatePrescription(prescriptionId, updatePrescriptionDTO)).thenReturn(updatedPrescription);

        ResponseEntity<?> response = prescriptionController.updatePrescription(prescriptionId, updatePrescriptionDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Prescription);
    }

    @Test
    public void testUpdatePrescriptionNotFound() {
        Long prescriptionId = 1L;
        UpdatePrescriptionDTO updatePrescriptionDTO = new UpdatePrescriptionDTO();

        when(prescriptionService.updatePrescription(prescriptionId, updatePrescriptionDTO)).thenThrow(PrescriptionNotFoundException.class);

        ResponseEntity<?> response = prescriptionController.updatePrescription(prescriptionId, updatePrescriptionDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeletePrescription() {
        Long prescriptionId = 1L;
        Prescription deletedPrescription = new Prescription();
        deletedPrescription.setId(prescriptionId);

        when(prescriptionService.deletePrescription(prescriptionId)).thenReturn(deletedPrescription);

        ResponseEntity<?> response = prescriptionController.deletePrescription(prescriptionId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Prescription);
    }

    @Test
    public void testDeletePrescriptionNotFound() {
        Long prescriptionId = 1L;

        when(prescriptionService.deletePrescription(prescriptionId)).thenThrow(PrescriptionNotFoundException.class);

        ResponseEntity<?> response = prescriptionController.deletePrescription(prescriptionId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
