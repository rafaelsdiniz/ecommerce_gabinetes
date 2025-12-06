package br.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
    @NotBlank(message = "Nome é obrigatório")
    String nome,
    
    String descricao
){}
