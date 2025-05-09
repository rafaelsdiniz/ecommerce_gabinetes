package br.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClienteJuridicoRequestDTO {

    @NotBlank(message = "email não pode ser nulo")
    private String email;

    @NotBlank(message = "telefone não pode ser nulo")
    private String telefone;

    @NotBlank(message = "nomeFantasia não pode ser nulo")
    private String nomeFantasia;

    @NotBlank(message = "razão social não pode ser nulo")
    private String razaoSocial;

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Pattern(
        regexp = "\\d{14}",
        message = "O CNPJ deve conter exatamente 14 dígitos numéricos (apenas números, sem pontos ou traços)."
    )
    private String cnpj;

    public ClienteJuridicoRequestDTO() {
    }

    public ClienteJuridicoRequestDTO(@NotBlank(message = "email não pode ser nulo") String email,
            @NotBlank(message = "telefone não pode ser nulo") String telefone,
            @NotBlank(message = "nomeFantasia não pode ser nulo") String nomeFantasia,
            @NotBlank(message = "razão social não pode ser nulo") String razaoSocial,
            @NotBlank(message = "O CNPJ é obrigatório.") @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos numéricos (apenas números, sem pontos ou traços).") String cnpj) {
        this.email = email;
        this.telefone = telefone;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    
}