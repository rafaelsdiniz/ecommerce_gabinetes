package br.dto;

import java.time.LocalDate;

public class ClienteFisicoResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataAniversario;

    private String telefone;
    private String email;
    private EnderecoResponseDTO endereco;
    
    public ClienteFisicoResponseDTO() {
    }

    public ClienteFisicoResponseDTO(Long id, String nome, String cpf, LocalDate dataAniversario, String telefone,
            String email, EnderecoResponseDTO endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataAniversario = dataAniversario;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
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

    public EnderecoResponseDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoResponseDTO endereco) {
        this.endereco = endereco;
    }

    
}
