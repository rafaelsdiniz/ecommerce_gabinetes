package br.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class ClienteFisico extends Cliente{
    private String nome;
    private String cpf;
    private LocalDate dataAniversario;
    
    public ClienteFisico() {
    }

    public ClienteFisico(String nome, String cpf, LocalDate dataAniversario) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataAniversario = dataAniversario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataAniversario() {
        return dataAniversario;
    }

    public void setDataAniversario(LocalDate dataAniversario) {
        this.dataAniversario = dataAniversario;
    }
     
}
