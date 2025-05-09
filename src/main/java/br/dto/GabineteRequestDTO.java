package br.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class GabineteRequestDTO {

    @NotBlank(message = "Nome de exibição é obrigatório")
    @Schema(description = "Nome do gabinete para exibição", example = "Gabinete Gamer RGB")
    private String nomeExibicao;

    @NotBlank(message = "Marca é obrigatória")
    @Schema(description = "Marca do gabinete", example = "Cooler Master")
    private String marca;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    @Schema(description = "Preço do gabinete", example = "299.99")
    private Double preco;

    @NotBlank(message = "Cor é obrigatória")
    @Schema(description = "Cor predominante do gabinete", example = "Preto")
    private String cor;

    @NotBlank(message = "Formato é obrigatório")
    @Schema(description = "Formato do gabinete", example = "ATX")
    private String formato;

    @PositiveOrZero(message = "Altura não pode ser negativa")
    @Schema(description = "Altura do gabinete em mm", example = "450")
    private Integer altura;

    @PositiveOrZero(message = "Largura não pode ser negativa")
    @Schema(description = "Largura do gabinete em mm", example = "210")
    private Integer largura;

    @PositiveOrZero(message = "Peso não pode ser negativo")
    @Schema(description = "Peso do gabinete em kg", example = "7.5")
    private Double peso;

    @PositiveOrZero(message = "Tamanho máximo da GPU não pode ser negativo")
    @Schema(description = "Tamanho máximo suportado para GPU em mm", example = "320")
    private Integer tamanhoMaxGpu;

    @PositiveOrZero(message = "Altura máxima do cooler não pode ser negativa")
    @Schema(description = "Altura máxima suportada para cooler em mm", example = "160")
    private Integer alturaMaxCooler;

    @PositiveOrZero(message = "quantidade de rgb não pode ser negativa")
    private Integer qtdRgb;

    @PositiveOrZero(message = "Número de USB não pode ser negativo")
    @Schema(description = "Quantidade de portas USB", example = "2")
    private Integer usb;

    @PositiveOrZero(message = "Número de USB-C não pode ser negativo")
    @Schema(description = "Quantidade de portas USB-C", example = "1")
    private Integer usbc;

    public GabineteRequestDTO() {}

    public GabineteRequestDTO(@NotBlank(message = "Nome de exibição é obrigatório") String nomeExibicao,
            @NotBlank(message = "Marca é obrigatória") String marca,
            @NotNull(message = "Preço é obrigatório") @Positive(message = "Preço deve ser positivo") Double preco,
            @NotBlank(message = "Cor é obrigatória") String cor,
            @NotBlank(message = "Formato é obrigatório") String formato,
            @PositiveOrZero(message = "Altura não pode ser negativa") Integer altura,
            @PositiveOrZero(message = "Largura não pode ser negativa") Integer largura,
            @PositiveOrZero(message = "Peso não pode ser negativo") Double peso,
            @PositiveOrZero(message = "Tamanho máximo da GPU não pode ser negativo") Integer tamanhoMaxGpu,
            @PositiveOrZero(message = "Altura máxima do cooler não pode ser negativa") Integer alturaMaxCooler,
            @PositiveOrZero(message = "quantidade de rgb não pode ser negativa") Integer qtdRgb,
            @PositiveOrZero(message = "Número de USB não pode ser negativo") Integer usb,
            @PositiveOrZero(message = "Número de USB-C não pode ser negativo") Integer usbc) {
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