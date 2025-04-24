package br.dto;

import java.time.LocalDate;

public class ClienteFisicoRequestDTO {
    private String nome;
    private String cpf;
    private LocalDate dataAniversario;

    private String telefone;
    private String email;
    private EnderecoRequestDTO endereco;

    
    public ClienteFisicoRequestDTO() {
    }

    public ClienteFisicoRequestDTO(String nome, String cpf, LocalDate dataAniversario, String telefone, String email,
        EnderecoRequestDTO endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataAniversario = dataAniversario;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnderecoRequestDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequestDTO endereco) {
        this.endereco = endereco;
    }

    
    
}
