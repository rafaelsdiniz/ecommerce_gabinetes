package br.dto.response;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(

    Long id,
    Long idGabinete,
    String nomeGabinete,
    Integer quantidade,
    BigDecimal precoUnitario,
    BigDecimal precoTotal

) {}
