package com.GenerativeAI.T1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private double price;
    @NotNull
    private int quantity;
}
