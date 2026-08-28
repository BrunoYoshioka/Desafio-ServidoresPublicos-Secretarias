package com.desafio.backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class SecretariaRequestDTO {

    private Long id;

    @NotBlank(message = "O nome da secretaria é obrigatório")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    private String nome;

    @NotBlank(message = "A sigla é obrigatória")
    @Size(max = 10, message = "A sigla deve ter no máximo 10 caracteres")
    private String sigla;
}
