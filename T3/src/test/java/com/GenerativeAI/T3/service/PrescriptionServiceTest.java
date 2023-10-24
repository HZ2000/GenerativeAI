package com.GenerativeAI.T3.service;

import com.GenerativeAI.T3.config.TestConfig;
import com.GenerativeAI.T3.dto.prescription.CreatePrescriptionDTO;
import com.GenerativeAI.T3.dto.prescription.UpdatePrescriptionDTO;
import com.GenerativeAI.T3.exception.AppointmentNotFoundException;
import com.GenerativeAI.T3.exception.PrescriptionNotFoundException;
import com.GenerativeAI.T3.model.Appointment;
import com.GenerativeAI.T3.model.Prescription;
import com.GenerativeAI.T3.repository.AppointmentRepository;
import com.GenerativeAI.T3.repository.PrescriptionRepository;
import com.GenerativeAI.T3.service.impl.PrescriptionServiceImpl;
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
public class PrescriptionServiceTest {

    @InjectMocks
    private PrescriptionServiceImpl prescriptionService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPrescriptionById() {
        Long prescriptionId = 1L;
        Prescription prescription = new Prescription();
        prescription.setId(prescriptionId);

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(prescription));

        Prescription result = prescriptionService.getPrescriptionById(prescriptionId);

        assertNotNull(result);
        assertEquals(prescriptionId, result.getId());
    }

    @Test
    public void testGetPrescriptionByIdNotFound() {
        Long prescriptionId = 1L;

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.empty());

        assertThrows(PrescriptionNotFoundException.class, () -> {
            prescriptionService.getPrescriptionById(prescriptionId);
        });
    }

    @Test
    public void testGetAllPrescriptionsByPatientId() {
        Long patientId = 1L;
        List<Prescription> prescriptions = new ArrayList<>();

        when(prescriptionRepository.findAllPrescriptionsByPatientId(patientId)).thenReturn(prescriptions);

        List<Prescription> result = prescriptionService.getAllPrescriptionsByPatientId(patientId);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetAllPrescriptionsByPatientIdNotFound() {
        Long patientId = 1L;
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(new Prescription());

        when(prescriptionRepository.findAllPrescriptionsByPatientId(patientId)).thenReturn(prescriptions);

        assertThrows(PrescriptionNotFoundException.class, () -> {
            prescriptionService.getAllPrescriptionsByPatientId(patientId);
        });
    }

    @Test
    public void testGetAllPrescriptionsByDoctorId() {
        Long doctorId = 1L;
        List<Prescription> prescriptions = new ArrayList<>();

        when(prescriptionRepository.findAllPrescriptionsByDoctorId(doctorId)).thenReturn(prescriptions);

        List<Prescription> result = prescriptionService.getAllPrescriptionsByDoctorId(doctorId);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetAllPrescriptionsByDoctorIdNotFound() {
        Long doctorId = 1L;
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(new Prescription());

        when(prescriptionRepository.findAllPrescriptionsByDoctorId(doctorId)).thenReturn(prescriptions);

        assertThrows(PrescriptionNotFoundException.class, () -> {
            prescriptionService.getAllPrescriptionsByDoctorId(doctorId);
        });
    }

    @Test
    public void testGetPrescriptionsByAppointmentId() {
        Long appointmentId = 1L;
        List<Prescription> prescriptions = new ArrayList<>();

        when(prescriptionRepository.findAllByAppointmentId(appointmentId)).thenReturn(prescriptions);

        List<Prescription> result = prescriptionService.getPrescriptionsByAppointmentId(appointmentId);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetPrescriptionsByAppointmentIdNotFound() {
        Long appointmentId = 1L;
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(new Prescription());

        when(prescriptionRepository.findAllByAppointmentId(appointmentId)).thenReturn(prescriptions);

        assertThrows(PrescriptionNotFoundException.class, () -> {
            prescriptionService.getPrescriptionsByAppointmentId(appointmentId);
        });
    }

    @Test
    public void testCreatePrescription() {
        CreatePrescriptionDTO createPrescriptionDTO = new CreatePrescriptionDTO();
        createPrescriptionDTO.setAppointmentId(1L);

        Appointment appointment = new Appointment();
        Prescription prescription = new Prescription();

        when(appointmentRepository.findById(createPrescriptionDTO.getAppointmentId())).thenReturn(Optional.of(appointment));
        when(modelMapper.map(createPrescriptionDTO, Prescription.class)).thenReturn(prescription);
        when(prescriptionRepository.save(prescription)).thenReturn(prescription);

        Prescription result = prescriptionService.createPrescription(createPrescriptionDTO);

        assertNotNull(result);
    }

    @Test
    public void testCreatePrescriptionAppointmentNotFound() {
        CreatePrescriptionDTO createPrescriptionDTO = new CreatePrescriptionDTO();
        createPrescriptionDTO.setAppointmentId(1L);

        when(appointmentRepository.findById(createPrescriptionDTO.getAppointmentId())).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            prescriptionService.createPrescription(createPrescriptionDTO);
        });
    }

    @Test
    public void testUpdatePrescription() {
        Long prescriptionId = 1L;
        UpdatePrescriptionDTO updatePrescriptionDTO = new UpdatePrescriptionDTO();
        Prescription prescription = new Prescription();

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(prescription));
        when(modelMapper.map(updatePrescriptionDTO, Prescription.class)).thenReturn(prescription);
        when(prescriptionRepository.save(prescription)).thenReturn(prescription);

        Prescription result = prescriptionService.updatePrescription(prescriptionId, updatePrescriptionDTO);

        assertNotNull(result);
        assertEquals(prescriptionId, result.getId());
    }

    @Test
    public void testUpdatePrescriptionNotFound() {
        Long prescriptionId = 1L;
        UpdatePrescriptionDTO updatePrescriptionDTO = new UpdatePrescriptionDTO();

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.empty());

        assertThrows(PrescriptionNotFoundException.class, () -> {
            prescriptionService.updatePrescription(prescriptionId, updatePrescriptionDTO);
        });
    }

    @Test
    public void testDeletePrescription() {
        Long prescriptionId = 1L;
        Prescription prescription = new Prescription();
        prescription.setId(prescriptionId);

        when(prescriptionRepository.existsById(prescriptionId)).thenReturn(true);
        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(prescription));

        Prescription result = prescriptionService.deletePrescription(prescriptionId);

        assertNotNull(result);
        assertEquals(prescriptionId, result.getId());
    }

    @Test
    public void testDeletePrescriptionNotFound() {
        Long prescriptionId = 1L;

        when(prescriptionRepository.existsById(prescriptionId)).thenReturn(false);

        assertThrows(PrescriptionNotFoundException.class, () -> {
            prescriptionService.deletePrescription(prescriptionId);
        });
    }
}
