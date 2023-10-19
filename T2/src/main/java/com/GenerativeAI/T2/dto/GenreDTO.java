package com.GenerativeAI.T2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenreDTO {
    @NotBlank
    private String name;
}
