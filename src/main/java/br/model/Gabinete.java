package br.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Gabinete extends DefaultEntity {

    private String nomeExibicao;
    private String marca;
    private Double preco;
    private String cor;
    private String formato;
    private Integer altura;
    private Integer largura;
    private Double peso;
    private Integer tamanhoMaxGpu;
    private Integer alturaMaxCooler;
    private Integer qtdRgb;
    private Integer usb;
    private Integer usbc;
    private String descricao;
    
    private String imagemKey;

    @ManyToMany
    @JoinTable(
        name = "gabinete_categoria",
        joinColumns = @JoinColumn(name = "gabinete_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @JsonIgnore
    private List<Categoria> categorias = new ArrayList<>();

    @OneToOne(mappedBy = "gabinete")
    private Estoque estoque;

    
    public Gabinete() {
    }

    public Gabinete(Integer altura, Integer alturaMaxCooler, String cor, String descricao, 
                   Estoque estoque, String formato, String imagemKey, Integer largura, 
                   String marca, String nomeExibicao, Double peso, Double preco, 
                   Integer qtdRgb, Integer tamanhoMaxGpu, Integer usb, Integer usbc) {
        this.altura = altura;
        this.alturaMaxCooler = alturaMaxCooler;
        this.cor = cor;
        this.descricao = descricao;
        this.estoque = estoque;
        this.formato = formato;
        this.imagemKey = imagemKey;
        this.largura = largura;
        this.marca = marca;
        this.nomeExibicao = nomeExibicao;
        this.peso = peso;
        this.preco = preco;
        this.qtdRgb = qtdRgb;
        this.tamanhoMaxGpu = tamanhoMaxGpu;
        this.usb = usb;
        this.usbc = usbc;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }

    public void setNomeExibicao(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getLargura() {
        return largura;
    }

    public void setLargura(Integer largura) {
        this.largura = largura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getTamanhoMaxGpu() {
        return tamanhoMaxGpu;
    }

    public void setTamanhoMaxGpu(Integer tamanhoMaxGpu) {
        this.tamanhoMaxGpu = tamanhoMaxGpu;
    }

    public Integer getAlturaMaxCooler() {
        return alturaMaxCooler;
    }

    public void setAlturaMaxCooler(Integer alturaMaxCooler) {
        this.alturaMaxCooler = alturaMaxCooler;
    }

    public Integer getQtdRgb() {
        return qtdRgb;
    }

    public void setQtdRgb(Integer qtdRgb) {
        this.qtdRgb = qtdRgb;
    }

    public Integer getUsb() {
        return usb;
    }

    public void setUsb(Integer usb) {
        this.usb = usb;
    }

    public Integer getUsbc() {
        return usbc;
    }

    public void setUsbc(Integer usbc) {
        this.usbc = usbc;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemKey() {
        return imagemKey;
    }

    public void setImagemKey(String imagemKey) {
        this.imagemKey = imagemKey;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    
    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

}