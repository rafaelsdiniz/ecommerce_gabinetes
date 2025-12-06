package br.dto.response;

import br.model.Estoque;

public record EstoqueResponseDTO(
    Long id,
    Integer quantidadeDisponivel,
    GabineteResponseDTO gabinete
) {
public static EstoqueResponseDTO valueOf(Estoque estoque) {
    return new EstoqueResponseDTO(
        estoque.getId(),
        estoque.getQuantidadeDisponivel(),
        GabineteResponseDTO.valueOf(estoque.getGabinete(), estoque.getQuantidadeDisponivel())
    );
}

}
