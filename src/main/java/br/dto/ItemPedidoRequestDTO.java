package br.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemPedidoRequestDTO {
    
    @NotNull(message = "id do pedido é obrigatório.")
    private Long idPedido;

    @NotNull(message = "id do gabinete é obrigatório.")
    private Long idGabinete;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    private Integer quantidade;

    @NotNull(message = "O preço unitário é obrigatório.")
    @Positive(message = "O preço unitário deve ser maior que zero.")
    private BigDecimal precoUnitario;

    public ItemPedidoRequestDTO() {
    }

    public ItemPedidoRequestDTO(Long idPedido, Long idGabinete, Integer quantidade, BigDecimal precoUnitario) {
        this.idPedido = idPedido;
        this.idGabinete = idGabinete;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdGabinete() {
        return idGabinete;
    }

    public void setIdGabinete(Long idGabinete) {
        this.idGabinete = idGabinete;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}