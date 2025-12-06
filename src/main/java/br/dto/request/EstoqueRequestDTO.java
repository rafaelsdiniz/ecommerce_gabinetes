package br.dto.request;

import jakarta.validation.constraints.NotNull;

public record EstoqueRequestDTO (
    @NotNull(message = "Id do gabinete é obrigatório")
    Long gabineteId,
    
    @NotNull(message = "Quantidade é obrigatório")
    Integer quantidadeDisponivel
){}
