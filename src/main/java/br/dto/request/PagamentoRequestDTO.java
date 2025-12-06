package br.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.model.enums.FormaPagamento;
import br.model.enums.StatusPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record PagamentoRequestDTO(
    @NotNull(message = "O ID do pedido é obrigatório")
    Long pedidoId,

    @NotNull(message = "Forma de pagamento é obrigatória")
    FormaPagamento formaPagamento,

    @NotNull(message = "Status do pagamento é obrigatório")
    StatusPagamento statusPagamento,

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior que zero")
    BigDecimal valor,

    @NotNull(message = "Data é obrigatória")
    @PastOrPresent(message = "A data do pagamento não pode estar no futuro")
    LocalDateTime data
) {}
