package com.GenerativeAI.T3.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private int age;
    private String phoneNumber;
    private String address;


    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
}
