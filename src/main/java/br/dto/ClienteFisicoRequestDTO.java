package br.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClienteFisicoRequestDTO {

    @NotBlank(message = "email não pode ser nulo")
    private String email;

    @NotBlank(message = "telefone não pode ser nulo")
    private String telefone;

    @NotBlank(message = "cpf não pode ser nulo")
    @Pattern(
        regexp = "\\d{11}",
        message = "O CPF deve conter exatamente 11 dígitos numéricos (apenas números, sem pontos ou traços)."
    )   
    private String cpf;

    @Min(value = 0, message = "Idade deve ser um número positivo.")
    private Integer idade;

    @NotBlank(message = "nome não pode ser nulo")
    private String nome;

    @NotBlank(message = "sobrenome não pode ser nulo")
    private String sobreNome;

    public ClienteFisicoRequestDTO() {
    }
    
    public ClienteFisicoRequestDTO(@NotBlank(message = "email não pode ser nulo") String email,
            @NotBlank(message = "telefone não pode ser nulo") String telefone,
            @NotBlank(message = "cpf não pode ser nulo") @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos (apenas números, sem pontos ou traços).") String cpf,
            @NotBlank(message = "idade não pode ser nulo") Integer idade,
            @NotBlank(message = "nome não pode ser nulo") String nome,
            @NotBlank(message = "sobrenome não pode ser nulo") String sobreNome) {
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.idade = idade;
        this.nome = nome;
        this.sobreNome = sobreNome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }



}