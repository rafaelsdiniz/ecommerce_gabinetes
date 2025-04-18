package br.dto;

public class CategoriaRequestDTO {
    private String nome;
    private String descricao;

    public CategoriaRequestDTO() {
    }

    public CategoriaRequestDTO(String descricao, String nome) {
        this.descricao = descricao;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}
