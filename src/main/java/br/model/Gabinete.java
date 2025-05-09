package br.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Gabinete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToMany
    @JoinTable(
        name = "gabinete_categoria",
        joinColumns = @JoinColumn(name = "gabinete_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @JsonIgnore
    private List<Categoria> categorias = new ArrayList<>();


    public Gabinete() {}

    

    public Gabinete(Long id, String nomeExibicao, String marca, Double preco, String cor, String formato,
            Integer altura, Integer largura, Double peso, Integer tamanhoMaxGpu, Integer alturaMaxCooler, Integer qtdRgb,
            Integer usb, Integer usbc, List<Categoria> categorias) {
        this.id = id;
        this.nomeExibicao = nomeExibicao;
        this.marca = marca;
        this.preco = preco;
        this.cor = cor;
        this.formato = formato;
        this.altura = altura;
        this.largura = largura;
        this.peso = peso;
        this.tamanhoMaxGpu = tamanhoMaxGpu;
        this.alturaMaxCooler = alturaMaxCooler;
        this.qtdRgb = qtdRgb;
        this.usb = usb;
        this.usbc = usbc;
        this.categorias = categorias;
    }



    public Long getId() {
        return id;
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

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}