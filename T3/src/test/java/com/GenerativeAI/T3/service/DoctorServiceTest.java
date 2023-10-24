package com.GenerativeAI.T3.service;

import com.GenerativeAI.T3.config.TestConfig;
import com.GenerativeAI.T3.dto.doctor.CreateDoctorDTO;
import com.GenerativeAI.T3.dto.doctor.UpdateDoctorDTO;
import com.GenerativeAI.T3.exception.DoctorNotFoundException;
import com.GenerativeAI.T3.model.Doctor;
import com.GenerativeAI.T3.repository.DoctorRepository;
import com.GenerativeAI.T3.service.impl.DoctorServiceImpl;
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
public class DoctorServiceTest {

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetDoctorById() {
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.getDoctorById(doctorId);

        assertNotNull(result);
        assertEquals(doctorId, result.getId());
    }

    @Test
    public void testGetDoctorByIdNotFound() {
        Long doctorId = 1L;

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> {
            doctorService.getDoctorById(doctorId);
        });
    }

    @Test
    public void testGetAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());
        doctors.add(new Doctor());

        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.getAllDoctors();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateDoctor() {
        CreateDoctorDTO doctorDTO = new CreateDoctorDTO();
        Doctor doctor = new Doctor();

        when(modelMapper.map(doctorDTO, Doctor.class)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        Doctor result = doctorService.createDoctor(doctorDTO);

        assertNotNull(result);
    }

    @Test
    public void testUpdateDoctorInfo() {
        Long doctorId = 1L;
        UpdateDoctorDTO doctorDTO = new UpdateDoctorDTO();
        Doctor existingDoctor = new Doctor();
        existingDoctor.setId(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(existingDoctor));
        when(doctorRepository.save(existingDoctor)).thenReturn(existingDoctor);

        Doctor result = doctorService.updateDoctorInfo(doctorId, doctorDTO);

        assertNotNull(result);
        assertEquals(doctorId, result.getId());
    }

    @Test
    public void testUpdateDoctorInfoNotFound() {
        Long doctorId = 1L;
        UpdateDoctorDTO doctorDTO = new UpdateDoctorDTO();

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> {
            doctorService.updateDoctorInfo(doctorId, doctorDTO);
        });
    }

    @Test
    public void testDeleteDoctor() {
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        when(doctorRepository.existsById(doctorId)).thenReturn(true);
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.deleteDoctor(doctorId);

        assertNotNull(result);
        assertEquals(doctorId, result.getId());
    }

    @Test
    public void testDeleteDoctorNotFound() {
        Long doctorId = 1L;

        when(doctorRepository.existsById(doctorId)).thenReturn(false);

        assertThrows(DoctorNotFoundException.class, () -> {
            doctorService.deleteDoctor(doctorId);
        });
    }
}
