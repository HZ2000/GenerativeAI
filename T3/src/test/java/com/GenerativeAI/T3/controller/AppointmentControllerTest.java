package com.GenerativeAI.T3.controller;

import com.GenerativeAI.T3.dto.appointment.CreateAppointmentDTO;
import com.GenerativeAI.T3.dto.appointment.UpdateAppointmentDTO;
import com.GenerativeAI.T3.exception.AppointmentNotFoundException;
import com.GenerativeAI.T3.exception.DoctorNotFoundException;
import com.GenerativeAI.T3.exception.PatientNotFoundException;
import com.GenerativeAI.T3.model.Appointment;
import com.GenerativeAI.T3.service.AppointmentService;
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

public class AppointmentControllerTest {

    @InjectMocks
    private AppointmentController appointmentController;

    @Mock
    private AppointmentService appointmentService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAppointment() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);

        when(appointmentService.getAppointmentById(appointmentId)).thenReturn(appointment);

        ResponseEntity<?> response = appointmentController.getAppointment(appointmentId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Appointment);
    }

    @Test
    public void testGetAppointmentNotFound() {
        Long appointmentId = 1L;

        when(appointmentService.getAppointmentById(appointmentId)).thenThrow(AppointmentNotFoundException.class);

        ResponseEntity<?> response = appointmentController.getAppointment(appointmentId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAppointmentsByPatient() {
        Long patientId = 1L;
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment());

        when(appointmentService.getAllAppointmentsByPatientId(patientId)).thenReturn(appointments);

        ResponseEntity<?> response = appointmentController.getAppointmentsByPatient(patientId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    public void testGetAppointmentsByPatientNotFound() {
        Long patientId = 1L;

        when(appointmentService.getAllAppointmentsByPatientId(patientId)).thenThrow(AppointmentNotFoundException.class);

        ResponseEntity<?> response = appointmentController.getAppointmentsByPatient(patientId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAppointmentsByDoctor() {
        Long doctorId = 1L;
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment());

        when(appointmentService.getAllAppointmentsByDoctorId(doctorId)).thenReturn(appointments);

        ResponseEntity<?> response = appointmentController.getAppointmentsByDoctor(doctorId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    public void testGetAppointmentsByDoctorNotFound() {
        Long doctorId = 1L;

        when(appointmentService.getAllAppointmentsByDoctorId(doctorId)).thenThrow(AppointmentNotFoundException.class);

        ResponseEntity<?> response = appointmentController.getAppointmentsByDoctor(doctorId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateAppointment() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();
        Appointment createdAppointment = new Appointment();

        when(appointmentService.createAppointment(createAppointmentDTO)).thenReturn(createdAppointment);

        ResponseEntity<?> response = appointmentController.createAppointment(createAppointmentDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Appointment);
    }

    @Test
    public void testCreateAppointmentWithPatientNotFound() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();

        when(appointmentService.createAppointment(createAppointmentDTO)).thenThrow(PatientNotFoundException.class);

        ResponseEntity<?> response = appointmentController.createAppointment(createAppointmentDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateAppointmentWithDoctorNotFound() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();

        when(appointmentService.createAppointment(createAppointmentDTO)).thenThrow(DoctorNotFoundException.class);

        ResponseEntity<?> response = appointmentController.createAppointment(createAppointmentDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateAppointment() {
        Long appointmentId = 1L;
        UpdateAppointmentDTO updateAppointmentDTO = new UpdateAppointmentDTO();
        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setId(appointmentId);

        when(appointmentService.updateAppointment(appointmentId, updateAppointmentDTO)).thenReturn(updatedAppointment);

        ResponseEntity<?> response = appointmentController.updateAppointment(appointmentId, updateAppointmentDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Appointment);
    }

    @Test
    public void testUpdateAppointmentNotFound() {
        Long appointmentId = 1L;
        UpdateAppointmentDTO updateAppointmentDTO = new UpdateAppointmentDTO();

        when(appointmentService.updateAppointment(appointmentId, updateAppointmentDTO)).thenThrow(AppointmentNotFoundException.class);

        ResponseEntity<?> response = appointmentController.updateAppointment(appointmentId, updateAppointmentDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateAppointmentWithPatientNotFound() {
        Long appointmentId = 1L;
        UpdateAppointmentDTO updateAppointmentDTO = new UpdateAppointmentDTO();

        when(appointmentService.updateAppointment(appointmentId, updateAppointmentDTO)).thenThrow(PatientNotFoundException.class);

        ResponseEntity<?> response = appointmentController.updateAppointment(appointmentId, updateAppointmentDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateAppointmentWithDoctorNotFound() {
        Long appointmentId = 1L;
        UpdateAppointmentDTO updateAppointmentDTO = new UpdateAppointmentDTO();

        when(appointmentService.updateAppointment(appointmentId, updateAppointmentDTO)).thenThrow(DoctorNotFoundException.class);

        ResponseEntity<?> response = appointmentController.updateAppointment(appointmentId, updateAppointmentDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteAppointment() {
        Long appointmentId = 1L;
        Appointment deletedAppointment = new Appointment();
        deletedAppointment.setId(appointmentId);

        when(appointmentService.deleteAppointment(appointmentId)).thenReturn(deletedAppointment);

        ResponseEntity<?> response = appointmentController.deleteAppointment(appointmentId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Appointment);
    }

    @Test
    public void testDeleteAppointmentNotFound() {
        Long appointmentId = 1L;

        when(appointmentService.deleteAppointment(appointmentId)).thenThrow(AppointmentNotFoundException.class);

        ResponseEntity<?> response = appointmentController.deleteAppointment(appointmentId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
