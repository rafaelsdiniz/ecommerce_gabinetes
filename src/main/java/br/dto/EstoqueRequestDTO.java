package br.dto;

public class EstoqueRequestDTO {
    private Long gabineteId;
    private Integer quantidadeDisponivel;

    public Long getGabineteId() {
        return gabineteId;
    }

    public void setGabineteId(Long gabineteId) {
        this.gabineteId = gabineteId;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
}
