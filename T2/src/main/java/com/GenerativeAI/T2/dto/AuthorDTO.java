package com.GenerativeAI.T2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorDTO {
    @NotBlank
    private String name;
}
