package br.dto.response;

import br.model.Categoria;

public record CategoriaResponseDTO(
    Long id,
    String nome,
    String descricao
) {
    public static CategoriaResponseDTO valueOf(Categoria entity) {
        if (entity == null)
            return null;

        return new CategoriaResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao()
        );
    }
}
