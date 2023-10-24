package com.GenerativeAI.T3.service;

import com.GenerativeAI.T3.config.TestConfig;
import com.GenerativeAI.T3.dto.appointment.CreateAppointmentDTO;
import com.GenerativeAI.T3.dto.appointment.UpdateAppointmentDTO;
import com.GenerativeAI.T3.exception.AppointmentNotFoundException;
import com.GenerativeAI.T3.exception.DoctorNotFoundException;
import com.GenerativeAI.T3.exception.PatientNotFoundException;
import com.GenerativeAI.T3.model.Appointment;
import com.GenerativeAI.T3.model.Doctor;
import com.GenerativeAI.T3.model.Patient;
import com.GenerativeAI.T3.repository.AppointmentRepository;
import com.GenerativeAI.T3.repository.DoctorRepository;
import com.GenerativeAI.T3.repository.PatientRepository;
import com.GenerativeAI.T3.service.impl.AppointmentServiceImpl;
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
public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAppointmentById() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.getAppointmentById(appointmentId);

        assertNotNull(result);
        assertEquals(appointmentId, result.getId());
    }

    @Test
    public void testGetAppointmentByIdNotFound() {
        Long appointmentId = 1L;

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.getAppointmentById(appointmentId);
        });
    }

    @Test
    public void testGetAllAppointmentsByPatientId() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment());
        appointments.add(new Appointment());

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findAppointmentsByPatient(patient)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAllAppointmentsByPatientId(patientId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllAppointmentsByPatientIdNotFound() {
        Long patientId = 1L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.getAllAppointmentsByPatientId(patientId);
        });
    }

    @Test
    public void testGetAllAppointmentsByDoctorId() {
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment());
        appointments.add(new Appointment());

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findAppointmentsByDoctor(doctor)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAllAppointmentsByDoctorId(doctorId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllAppointmentsByDoctorIdNotFound() {
        Long doctorId = 1L;

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.getAllAppointmentsByDoctorId(doctorId);
        });
    }

    @Test
    public void testCreateAppointment() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();
        createAppointmentDTO.setPatient(new Patient());
        createAppointmentDTO.setDoctor(new Doctor());

        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        Appointment appointment = new Appointment();

        when(patientRepository.findById(createAppointmentDTO.getPatient().getId())).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(createAppointmentDTO.getDoctor().getId())).thenReturn(Optional.of(doctor));
        when(modelMapper.map(createAppointmentDTO, Appointment.class)).thenReturn(appointment);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        Appointment result = appointmentService.createAppointment(createAppointmentDTO);

        assertNotNull(result);
    }

    @Test
    public void testCreateAppointmentPatientNotFound() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();
        createAppointmentDTO.setPatient(new Patient());
        createAppointmentDTO.setDoctor(new Doctor());

        when(patientRepository.findById(createAppointmentDTO.getPatient().getId())).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> {
            appointmentService.createAppointment(createAppointmentDTO);
        });
    }

    @Test
    public void testCreateAppointmentDoctorNotFound() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();
        createAppointmentDTO.setPatient(new Patient());
        createAppointmentDTO.setDoctor(new Doctor());

        when(patientRepository.findById(createAppointmentDTO.getPatient().getId())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(createAppointmentDTO.getDoctor().getId())).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> {
            appointmentService.createAppointment(createAppointmentDTO);
        });
    }

    @Test
    public void testUpdateAppointment() {
        Long appointmentId = 1L;
        UpdateAppointmentDTO updateAppointmentDTO = new UpdateAppointmentDTO();
        Patient existingPatient = new Patient();
        existingPatient.setId(1L);
        Doctor existingDoctor = new Doctor();
        existingDoctor.setId(1L);
        Appointment existingAppointment = new Appointment();
        existingAppointment.setId(appointmentId);
        updateAppointmentDTO.setPatient(existingPatient);
        updateAppointmentDTO.setDoctor(existingDoctor);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));
        when(patientRepository.findById(updateAppointmentDTO.getPatient().getId())).thenReturn(Optional.of(existingPatient));
        when(doctorRepository.findById(updateAppointmentDTO.getDoctor().getId())).thenReturn(Optional.of(existingDoctor));
        when(appointmentRepository.save(existingAppointment)).thenReturn(existingAppointment);

        Appointment result = appointmentService.updateAppointment(appointmentId, updateAppointmentDTO);

        assertNotNull(result);
    }

    @Test
    public void testUpdateAppointmentPatientNotFound() {
        Long appointmentId = 1L;
        UpdateAppointmentDTO updateAppointmentDTO = new UpdateAppointmentDTO();
        updateAppointmentDTO.setPatient(new Patient());
        updateAppointmentDTO.getPatient().setId(1L);
        updateAppointmentDTO.setDoctor(new Doctor());
        updateAppointmentDTO.getDoctor().setId(1L);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(new Appointment()));
        when(patientRepository.findById(updateAppointmentDTO.getPatient().getId())).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> {
            appointmentService.updateAppointment(appointmentId, updateAppointmentDTO);
        });
    }

    @Test
    public void testUpdateAppointmentDoctorNotFound() {
        Long appointmentId = 1L;
        UpdateAppointmentDTO updateAppointmentDTO = new UpdateAppointmentDTO();
        updateAppointmentDTO.setDoctor(new Doctor());
        updateAppointmentDTO.getDoctor().setId(1L);
        updateAppointmentDTO.setPatient(new Patient());
        updateAppointmentDTO.setDoctor(new Doctor());

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(new Appointment()));
        when(patientRepository.findById(updateAppointmentDTO.getPatient().getId())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(updateAppointmentDTO.getDoctor().getId())).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> {
            appointmentService.updateAppointment(appointmentId, updateAppointmentDTO);
        });
    }

    @Test
    public void testUpdateAppointmentNotFound() {
        Long appointmentId = 1L;
        UpdateAppointmentDTO updateAppointmentDTO = new UpdateAppointmentDTO();
        updateAppointmentDTO.setPatient(new Patient());
        updateAppointmentDTO.getPatient().setId(1L);
        updateAppointmentDTO.setDoctor(new Doctor());
        updateAppointmentDTO.getDoctor().setId(1L);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> {
            appointmentService.updateAppointment(appointmentId, updateAppointmentDTO);
        });
    }

    @Test
    public void testDeleteAppointment() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);

        when(appointmentRepository.existsById(appointmentId)).thenReturn(true);
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.deleteAppointment(appointmentId);

        assertNotNull(result);
        assertEquals(appointmentId, result.getId());
    }

    @Test
    public void testDeleteAppointmentNotFound() {
        Long appointmentId = 1L;

        when(appointmentRepository.existsById(appointmentId)).thenReturn(false);

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.deleteAppointment(appointmentId);
        });
    }
}
