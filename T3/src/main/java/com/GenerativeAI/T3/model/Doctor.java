package com.GenerativeAI.T3.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String specialization;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
}
