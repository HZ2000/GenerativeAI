package com.GenerativeAI.T3.service;

import com.GenerativeAI.T3.dto.prescription.CreatePrescriptionDTO;
import com.GenerativeAI.T3.dto.prescription.UpdatePrescriptionDTO;
import com.GenerativeAI.T3.model.Prescription;

import java.util.List;

public interface PrescriptionService {
    Prescription getPrescriptionById(Long id);
    List<Prescription> getAllPrescriptionsByPatientId(Long patientId);
    List<Prescription> getAllPrescriptionsByDoctorId(Long doctorId);
    List<Prescription> getPrescriptionsByAppointmentId(Long appointmentId);
    Prescription createPrescription(CreatePrescriptionDTO createPrescriptionDTO);
    Prescription updatePrescription(Long prescriptionId, UpdatePrescriptionDTO createPrescriptionDTO);
    Prescription deletePrescription(Long id);
}
