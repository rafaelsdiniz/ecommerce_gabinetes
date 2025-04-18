package br.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Categoria {
    @Id
    @GeneratedValue

    private Long id;
    
    @Column(nullable = false, unique = true) // coluna nao pode ser nula e tem que ser unica
    private String nome;

    @Column(length= 1000) // descricao pode ser no mxm 1000 caracteres
    private String descricao;
    
    @ManyToMany(mappedBy = "categorias")
    private List<Gabinete> gabinetes = new ArrayList<>();

    public Categoria() {
    }

    public Categoria(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
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

    public List<Gabinete> getGabinetes() {
        return gabinetes;
    }

    public void setGabinetes(List<Gabinete> gabinetes) {
        this.gabinetes = gabinetes;
    }
    
}
