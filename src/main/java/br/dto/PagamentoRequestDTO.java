package br.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.enums.FormaPagamento;
import br.enums.StatusPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class PagamentoRequestDTO {

    @NotNull(message = "O ID do pedido é obrigatório")
    private Long pedidoId;

    @NotNull(message = "Forma de pagamento é obrigatória")
    private FormaPagamento formaPagamento;

    @NotNull(message = "Status do pagamento é obrigatório")
    private StatusPagamento statusPagamento;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    @PastOrPresent(message = "A data do pagamento não pode estar no futuro")
    private LocalDateTime data;

    public PagamentoRequestDTO() {
    }

    public PagamentoRequestDTO(@NotNull(message = "O ID do pedido é obrigatório") Long pedidoId,
            @NotNull(message = "Forma de pagamento é obrigatória") FormaPagamento formaPagamento,
            @NotNull(message = "Status do pagamento é obrigatório") StatusPagamento statusPagamento,
            @NotNull(message = "Valor é obrigatório") @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior que zero") BigDecimal valor,
            @NotNull(message = "Data é obrigatória") @PastOrPresent(message = "A data do pagamento não pode estar no futuro") LocalDateTime data) {
        this.pedidoId = pedidoId;
        this.formaPagamento = formaPagamento;
        this.statusPagamento = statusPagamento;
        this.valor = valor;
        this.data = data;
    }


    
    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    
}