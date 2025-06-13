package br.dto;

public class EstoqueResponseDTO {
    private Long id;
    private Long gabineteId;
    private String nomeGabinete;
    private Integer quantidadeDisponivel;

    public EstoqueResponseDTO(Long id, Long gabineteId, String nomeGabinete, Integer quantidadeDisponivel) {
        this.id = id;
        this.gabineteId = gabineteId;
        this.nomeGabinete = nomeGabinete;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Long getId() {
        return id;
    }

    public Long getGabineteId() {
        return gabineteId;
    }

    public String getNomeGabinete() {
        return nomeGabinete;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
}
