package br.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Estoque extends DefaultEntity {

    @OneToOne
    @JoinColumn(name= "gabinete_id", nullable= false, unique= true)
    private Gabinete gabinete;

    @Column(name= "quantidade_disponivel", nullable= false)
    private Integer quantidadeDisponivel;

    public Estoque() {
    }

    public Estoque(Gabinete gabinete, Integer quantidadeDisponivel) {
        this.gabinete = gabinete;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Gabinete getGabinete() {
        return gabinete;
    }

    public void setGabinete(Gabinete gabinete) {
        this.gabinete = gabinete;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
    
}
