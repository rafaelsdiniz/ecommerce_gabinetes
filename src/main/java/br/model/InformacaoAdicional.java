package br.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class InformacaoAdicional extends DefaultEntity {
    
    @Column(nullable = false, length = 100)
    private String titulo;
    
    @Column(nullable = false, length = 500)
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name = "gabinete_id", nullable = false)
    private Gabinete gabinete;

    public InformacaoAdicional() {
    }

    public InformacaoAdicional(String titulo, String descricao, Gabinete gabinete) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.gabinete = gabinete;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Gabinete getGabinete() {
        return gabinete;
    }

    public void setGabinete(Gabinete gabinete) {
        this.gabinete = gabinete;
    }
    
}
