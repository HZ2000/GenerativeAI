package com.GenerativeAI.T3.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String medicationName;
    private String dosage;
    private String instructions;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
