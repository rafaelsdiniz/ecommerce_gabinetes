package br.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FornecedorRequestDTO (
    @NotBlank String nome,
    @NotBlank String email,
    @NotBlank String telefone,
    @NotBlank String cnpj
){}
