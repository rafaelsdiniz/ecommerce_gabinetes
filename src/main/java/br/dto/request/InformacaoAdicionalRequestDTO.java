package br.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InformacaoAdicionalRequestDTO(
    @NotBlank(message = "Título é obrigatório")
    String titulo,
    
    @NotBlank(message = "Descrição é obrigatória")
    String descricao,
    
    @NotNull(message = "ID do gabinete é obrigatório")
    Long gabineteId
) {
    
}
