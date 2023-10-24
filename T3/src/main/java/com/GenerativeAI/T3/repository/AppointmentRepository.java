package com.GenerativeAI.T3.repository;

import com.GenerativeAI.T3.model.Appointment;
import com.GenerativeAI.T3.model.Doctor;
import com.GenerativeAI.T3.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAppointmentsByPatient(Patient patient);
    List<Appointment> findAppointmentsByDoctor(Doctor doctor);
}
