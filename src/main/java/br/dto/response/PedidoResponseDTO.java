package br.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.model.Endereco;
import br.model.enums.StatusPedido;

public record PedidoResponseDTO(
    Long id,
    ClienteResponseDTO cliente,  
    LocalDateTime dataPedido,
    List<ItemPedidoResponseDTO> itens,
    BigDecimal valorTotal,
    StatusPedido status,
    Endereco endereco
) {}

