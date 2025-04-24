package br.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.enums.FormaPagamento;
import br.enums.StatusPagamento;

public class PagamentoResponseDTO {
    private Long id;
    private Long pedidoId;
    private FormaPagamento formaPagamento;
    private StatusPagamento statusPagamento;
    private BigDecimal valor;
    private LocalDateTime data;

    public PagamentoResponseDTO() {
    }

    public PagamentoResponseDTO(Long id, Long pedidoId, FormaPagamento formaPagamento,
            StatusPagamento statusPagamento, BigDecimal valor, LocalDateTime data) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.formaPagamento = formaPagamento;
        this.statusPagamento = statusPagamento;
        this.valor = valor;
        this.data = data;
    }

    public Long getId() {
        return id;
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
