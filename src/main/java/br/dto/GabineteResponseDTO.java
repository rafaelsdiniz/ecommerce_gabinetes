package br.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta para informações de gabinete")
public class GabineteResponseDTO {
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

    public GabineteResponseDTO() {}

    public GabineteResponseDTO(Long id, String nomeExibicao, String marca, Double preco, String cor, String formato,
            Integer altura, Integer largura, Double peso, Integer tamanhoMaxGpu, Integer alturaMaxCooler,
            Integer qtdRgb, Integer usb, Integer usbc) {
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


}