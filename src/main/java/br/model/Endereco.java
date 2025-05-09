package br.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Endereco {

    @NotBlank(message = "Estado é obrigatório")
    @Column(nullable = false)
    private String estado;

    @NotBlank(message = "Cidade é obrigatória")
    @Column(nullable = false)
    private String cidade;

    @NotBlank(message = "Bairro é obrigatório")
    @Column(nullable = false)
    private String bairro;

    @NotBlank(message = "CEP é obrigatório")
    @Column(nullable = false)
    private String cep;

    @NotBlank(message = "Número é obrigatório")
    @Column(nullable = false)
    private String numero;

    @NotBlank(message = "Logradouro é obrigatório")
    @Column(nullable = false)
    private String logradouro;

    public Endereco() {}

    public Endereco(String estado, String cidade, String bairro, String cep, String numero, String logradouro) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
        this.numero = numero;
        this.logradouro = logradouro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    
}