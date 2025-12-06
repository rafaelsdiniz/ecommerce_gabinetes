package br.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record ModeloRequestDTO(
    @NotBlank(message = "Nome do modelo é obrigatório")
    String nomeModelo,
    
    @NotNull(message = "ID da marca é obrigatório")
    Long marcaId
) {
    
}
