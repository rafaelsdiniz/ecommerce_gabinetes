package br.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.enums.FormaPagamento;
import br.enums.StatusPagamento;
import br.model.Pagamento;

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

    public PagamentoResponseDTO(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.pedidoId = pagamento.getPedido().getId();  
        this.formaPagamento = pagamento.getFormaPagamento();
        this.statusPagamento = pagamento.getStatusPagamento();
        this.valor = pagamento.getValor();
        this.data = pagamento.getData();
    }

    public Long getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getData() {
        return data;
    }
}
