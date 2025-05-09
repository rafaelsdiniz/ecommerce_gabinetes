package br.dto;

import java.math.BigDecimal;

public class ItemPedidoResponseDTO {
    private Long id;
    private Long gabineteId;
    private String nomeGabinete;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;

    public ItemPedidoResponseDTO() {
    }

    public ItemPedidoResponseDTO(Long id, Long gabineteId, String nomeGabinete, Integer quantidade, 
                               BigDecimal precoUnitario, BigDecimal precoTotal) {
        this.id = id;
        this.gabineteId = gabineteId;
        this.nomeGabinete = nomeGabinete;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.precoTotal = precoTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGabineteId() {
        return gabineteId;
    }

    public void setGabineteId(Long gabineteId) {
        this.gabineteId = gabineteId;
    }

    public String getNomeGabinete() {
        return nomeGabinete;
    }

    public void setNomeGabinete(String nomeGabinete) {
        this.nomeGabinete = nomeGabinete;
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

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }
}