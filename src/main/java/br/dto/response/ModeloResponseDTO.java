package br.dto.response;

import br.model.Modelo;

public record ModeloResponseDTO(
    Long id,
    String nomeModelo,
    Long marcaId,
    String nomeMarca
) {
    public static ModeloResponseDTO valueOf(Modelo modelo) {
        return new ModeloResponseDTO(
            modelo.getId(),
            modelo.getNomeModelo(),
            modelo.getMarca().getId(),
            modelo.getMarca().getNome()
        );
    }
}
