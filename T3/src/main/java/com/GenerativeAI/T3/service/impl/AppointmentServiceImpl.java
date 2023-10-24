package com.GenerativeAI.T3.service.impl;

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
import com.GenerativeAI.T3.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public Appointment getAppointmentById(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return appointment.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));
    }

    @Override
    public List<Appointment> getAllAppointmentsByPatientId(Long patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if(patient.isPresent()) {
            Patient existingPatient = patient.get();
            return appointmentRepository.findAppointmentsByPatient(existingPatient);
        }
        throw new AppointmentNotFoundException("Appointments not found with Patient ID: " + patientId);
    }

    @Override
    public List<Appointment> getAllAppointmentsByDoctorId(Long doctorId) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if(doctor.isPresent()) {
            Doctor existingDoctor = doctor.get();
            return appointmentRepository.findAppointmentsByDoctor(existingDoctor);
        }
        throw new AppointmentNotFoundException("Appointments not found with Doctor ID: " + doctorId);
    }

    @Override
    public Appointment createAppointment(CreateAppointmentDTO createAppointmentDTO) {
        Optional<Patient> patient = patientRepository.findById(createAppointmentDTO.getPatient().getId());
        Optional<Doctor> doctor = doctorRepository.findById(createAppointmentDTO.getDoctor().getId());
        if(patient.isPresent() && doctor.isPresent()) {
            Patient existingPatient = patient.get();
            Doctor existingDoctor = doctor.get();
            Appointment appointment = modelMapper.map(createAppointmentDTO, Appointment.class);
            appointment.setPatient(existingPatient);
            appointment.setDoctor(existingDoctor);
            return appointmentRepository.save(appointment);
        } else if(patient.isEmpty()) {
            throw new PatientNotFoundException("Patient not found with ID: " + createAppointmentDTO.getPatient().getId());
        } else {
            throw new DoctorNotFoundException("Doctor not found with ID: " + createAppointmentDTO.getDoctor().getId());
        }
    }

    @Override
    public Appointment updateAppointment(Long appointmentId, UpdateAppointmentDTO updateAppointmentDTO) {
        Optional<Patient> patient = patientRepository.findById(updateAppointmentDTO.getPatient().getId());
        Optional<Doctor> doctor = doctorRepository.findById(updateAppointmentDTO.getDoctor().getId());
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if(patient.isPresent() && doctor.isPresent() && appointment.isPresent()) {
            Patient existingPatient = patient.get();
            Doctor existingDoctor = doctor.get();
            Appointment existingAppointment = appointment.get();
            modelMapper.map(updateAppointmentDTO, existingAppointment);
            existingAppointment.setId(appointmentId);
            existingAppointment.setPatient(existingPatient);
            existingAppointment.setDoctor(existingDoctor);
            return appointmentRepository.save(existingAppointment);
        } else if(patient.isEmpty()) {
            throw new PatientNotFoundException("Patient not found with ID: " + updateAppointmentDTO.getPatient().getId());
        } else if(doctor.isEmpty()) {
            throw new DoctorNotFoundException("Doctor not found with ID: " + updateAppointmentDTO.getDoctor().getId());
        } else {
            throw new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId);
        }
    }

    @Override
    public Appointment deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new AppointmentNotFoundException("Appointment not found with ID: " + id);
        }
        Appointment appointment = getAppointmentById(id);
        appointmentRepository.deleteById(id);
        return appointment;
    }
}
