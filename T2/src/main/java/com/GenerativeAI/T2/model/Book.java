package com.GenerativeAI.T2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Author author;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Genre genre;

    private BigDecimal price;
    private int quantity;
}
