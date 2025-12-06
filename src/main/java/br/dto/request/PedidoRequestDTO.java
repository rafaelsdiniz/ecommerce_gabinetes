package br.dto.request;

import java.util.List;

import br.model.enums.StatusPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(
    @NotEmpty(message = "Lista de itens não pode ser vazia")
    @Valid
    List<ItemPedidoRequestDTO> itens,

    @NotNull(message = "Endereço é obrigatório")
    @Valid
    EnderecoRequestDTO endereco,

    StatusPedido status
) {}
