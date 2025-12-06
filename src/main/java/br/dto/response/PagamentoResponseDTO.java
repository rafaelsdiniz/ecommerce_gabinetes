package br.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import br.model.enums.FormaPagamento;
import br.model.enums.StatusPagamento;

public record PagamentoResponseDTO(

    Long id,
    Long pedidoId,
    FormaPagamento formaPagamento,
    StatusPagamento statusPagamento,
    BigDecimal valor,
    LocalDateTime data

) {}
