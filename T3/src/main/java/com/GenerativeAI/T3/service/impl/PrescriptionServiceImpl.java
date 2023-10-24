package com.GenerativeAI.T3.service.impl;

import com.GenerativeAI.T3.dto.prescription.CreatePrescriptionDTO;
import com.GenerativeAI.T3.dto.prescription.UpdatePrescriptionDTO;
import com.GenerativeAI.T3.exception.AppointmentNotFoundException;
import com.GenerativeAI.T3.exception.PrescriptionNotFoundException;
import com.GenerativeAI.T3.model.Appointment;
import com.GenerativeAI.T3.model.Prescription;
import com.GenerativeAI.T3.repository.AppointmentRepository;
import com.GenerativeAI.T3.repository.PrescriptionRepository;
import com.GenerativeAI.T3.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final ModelMapper modelMapper;
    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public Prescription getPrescriptionById(Long id) {
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        return prescription.orElseThrow(() -> new PrescriptionNotFoundException("Prescription not found with ID: " + id));
    }

    @Override
    public List<Prescription> getAllPrescriptionsByPatientId(Long patientId) {
        List<Prescription> prescriptions = prescriptionRepository.findAllPrescriptionsByPatientId(patientId);
        if(!prescriptions.isEmpty()) {
            throw new PrescriptionNotFoundException("Prescriptions not found with Patient ID: " + patientId);
        }
        return prescriptions;
    }

    @Override
    public List<Prescription> getAllPrescriptionsByDoctorId(Long doctorId) {
        List<Prescription> prescriptions = prescriptionRepository.findAllPrescriptionsByDoctorId(doctorId);
        if(!prescriptions.isEmpty()) {
            throw new PrescriptionNotFoundException("Prescriptions not found with Doctor ID: " + doctorId);
        }
        return prescriptions;
    }

    @Override
    public List<Prescription> getPrescriptionsByAppointmentId(Long appointmentId) {
        List<Prescription> prescriptions = prescriptionRepository.findAllByAppointmentId(appointmentId);
        if(!prescriptions.isEmpty()) {
            throw new PrescriptionNotFoundException("Prescriptions not found with Appointment ID: " + appointmentId);
        }
        return prescriptions;
    }

    @Override
    public Prescription createPrescription(CreatePrescriptionDTO createPrescriptionDTO) {
        Optional<Appointment> appointment = appointmentRepository.findById(createPrescriptionDTO.getAppointmentId());
        if(appointment.isPresent()) {
            Appointment existingAppointment = appointment.get();
            Prescription prescription = modelMapper.map(createPrescriptionDTO, Prescription.class);
            prescription.setAppointment(existingAppointment);
            return prescriptionRepository.save(prescription);
        }
        throw new AppointmentNotFoundException("Appointment not found with Appointment ID: " + createPrescriptionDTO.getAppointmentId());
    }

    @Override
    public Prescription updatePrescription(Long prescriptionId, UpdatePrescriptionDTO createPrescriptionDTO) {
        Optional<Prescription> prescription = prescriptionRepository.findById(prescriptionId);
        if(prescription.isPresent()) {
            Prescription existingPrescription = prescription.get();
            modelMapper.map(createPrescriptionDTO, existingPrescription);
            existingPrescription.setId(prescriptionId);
            existingPrescription = prescriptionRepository.save(existingPrescription);
            return existingPrescription;
        } else {
            throw new PrescriptionNotFoundException("Prescription not found with ID: " + prescriptionId);
        }
    }

    @Override
    public Prescription deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new PrescriptionNotFoundException("Prescription not found with ID: " + id);
        }
        Prescription prescription = getPrescriptionById(id);
        prescriptionRepository.deleteById(id);
        return prescription;
    }
}
