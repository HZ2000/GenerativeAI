package com.GenerativeAI.T2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDTO {
    @NotBlank
    private String title;
    @NotNull
    private AuthorDTO author;
    @NotNull
    private GenreDTO genre;
    @NotNull
    private BigDecimal price;
    @NotNull
    private int quantity;
}
