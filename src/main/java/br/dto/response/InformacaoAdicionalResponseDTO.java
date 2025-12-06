package br.dto.response;

import br.model.InformacaoAdicional;

public record InformacaoAdicionalResponseDTO(
    Long id,
    String titulo,
    String descricao,
    Long gabineteId,
    String nomeGabinete
) {
    public static InformacaoAdicionalResponseDTO valueOf(InformacaoAdicional info) {
        return new InformacaoAdicionalResponseDTO(
            info.getId(),
            info.getTitulo(),
            info.getDescricao(),
            info.getGabinete().getId(),
            info.getGabinete().getNomeExibicao()
        );
    }
}
