package com.GenerativeAI.T3.repository;

import com.GenerativeAI.T3.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findAllByAppointmentId(Long appointmentId);
    List<Prescription> findAllPrescriptionsByPatientId(Long patientId);
    List<Prescription> findAllPrescriptionsByDoctorId(Long doctorId);
}
