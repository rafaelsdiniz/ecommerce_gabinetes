package br.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequestDTO(

    @NotNull(message = "id do pedido é obrigatório.")
    Long idPedido,

    @NotNull(message = "id do gabinete é obrigatório.")
    Long idGabinete,

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    Integer quantidade
    
) {}