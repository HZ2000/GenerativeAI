package com.GenerativeAI.T3.controller;

import com.GenerativeAI.T3.dto.doctor.CreateDoctorDTO;
import com.GenerativeAI.T3.dto.doctor.UpdateDoctorDTO;
import com.GenerativeAI.T3.exception.DoctorNotFoundException;
import com.GenerativeAI.T3.model.Doctor;
import com.GenerativeAI.T3.service.DoctorService;
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

public class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetDoctor() {
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        when(doctorService.getDoctorById(doctorId)).thenReturn(doctor);

        ResponseEntity<?> response = doctorController.getDoctor(doctorId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetDoctorNotFound() {
        Long doctorId = 1L;

        when(doctorService.getDoctorById(doctorId)).thenThrow(DoctorNotFoundException.class);

        ResponseEntity<?> response = doctorController.getDoctor(doctorId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());

        when(doctorService.getAllDoctors()).thenReturn(doctors);

        List<Doctor> result = doctorController.getAllDoctors();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testCreateDoctor() {
        CreateDoctorDTO createDoctorDTO = new CreateDoctorDTO();
        Doctor createdDoctor = new Doctor();

        when(doctorService.createDoctor(createDoctorDTO)).thenReturn(createdDoctor);

        ResponseEntity<Doctor> response = doctorController.createDoctor(createDoctorDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDoctor, response.getBody());
    }

    @Test
    public void testUpdateDoctor() {
        Long doctorId = 1L;
        UpdateDoctorDTO updateDoctorDTO = new UpdateDoctorDTO();
        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setId(doctorId);

        when(doctorService.updateDoctorInfo(doctorId, updateDoctorDTO)).thenReturn(updatedDoctor);

        ResponseEntity<?> response = doctorController.updateDoctor(doctorId, updateDoctorDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Doctor);
    }

    @Test
    public void testUpdateDoctorNotFound() {
        Long doctorId = 1L;
        UpdateDoctorDTO updateDoctorDTO = new UpdateDoctorDTO();

        when(doctorService.updateDoctorInfo(doctorId, updateDoctorDTO)).thenThrow(DoctorNotFoundException.class);

        ResponseEntity<?> response = doctorController.updateDoctor(doctorId, updateDoctorDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteDoctor() {
        Long doctorId = 1L;
        Doctor deletedDoctor = new Doctor();
        deletedDoctor.setId(doctorId);

        when(doctorService.deleteDoctor(doctorId)).thenReturn(deletedDoctor);

        ResponseEntity<?> response = doctorController.deleteDoctor(doctorId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Doctor);
    }

    @Test
    public void testDeleteDoctorNotFound() {
        Long doctorId = 1L;

        when(doctorService.deleteDoctor(doctorId)).thenThrow(DoctorNotFoundException.class);

        ResponseEntity<?> response = doctorController.deleteDoctor(doctorId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
