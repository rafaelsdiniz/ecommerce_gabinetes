package br.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MarcaRequestDTO (

    @NotBlank(message = "Nome é obrigatório")
    String nome,

    String descricao

){}
