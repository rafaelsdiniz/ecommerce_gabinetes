package br.dto;

import jakarta.validation.constraints.NotBlank;

public class MarcaRequestDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    public MarcaRequestDTO() {}

    public MarcaRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
