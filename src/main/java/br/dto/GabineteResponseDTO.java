package br.dto;

public class GabineteResponseDTO {
    
    private long id;
    private String nomeExibicao;
    private String marca;
    private Double preco;
    private String cor;
    private String formato;
    private int altura;
    private int largura;
    private double peso;
    private boolean suportaWaterCooler;
    private int tamanhoMaxGpu;
    private int alturaMaxCooler;
    private boolean rgb;
    private int usb;
    private int usbc;

    public GabineteResponseDTO() {
    }

    public GabineteResponseDTO(long id, String nomeExibicao, String marca, Double preco, String cor, String formato,
            int altura, int largura, double peso, boolean suportaWaterCooler, int tamanhoMaxGpu, int alturaMaxCooler,
            boolean rgb, int usb, int usbc) {
        this.id = id;
        this.nomeExibicao = nomeExibicao;
        this.marca = marca;
        this.preco = preco;
        this.cor = cor;
        this.formato = formato;
        this.altura = altura;
        this.largura = largura;
        this.peso = peso;
        this.suportaWaterCooler = suportaWaterCooler;
        this.tamanhoMaxGpu = tamanhoMaxGpu;
        this.alturaMaxCooler = alturaMaxCooler;
        this.rgb = rgb;
        this.usb = usb;
        this.usbc = usbc;
    }

    public long getId() {
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

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public boolean isSuportaWaterCooler() {
        return suportaWaterCooler;
    }

    public void setSuportaWaterCooler(boolean suportaWaterCooler) {
        this.suportaWaterCooler = suportaWaterCooler;
    }

    public int getTamanhoMaxGpu() {
        return tamanhoMaxGpu;
    }

    public void setTamanhoMaxGpu(int tamanhoMaxGpu) {
        this.tamanhoMaxGpu = tamanhoMaxGpu;
    }

    public int getAlturaMaxCooler() {
        return alturaMaxCooler;
    }

    public void setAlturaMaxCooler(int alturaMaxCooler) {
        this.alturaMaxCooler = alturaMaxCooler;
    }

    public int getUsb() {
        return usb;
    }

    public void setUsb(int usb) {
        this.usb = usb;
    }

    public int getUsbc() {
        return usbc;
    }

    public void setUsbc(int usbc) {
        this.usbc = usbc;
    }

    public boolean isRgb() {
        return rgb;
    }

    public void setRgb(boolean rgb) {
        this.rgb = rgb;
    }

    

    
}
