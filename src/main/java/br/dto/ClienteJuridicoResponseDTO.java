package br.dto;

public class ClienteJuridicoResponseDTO {

    private Long id;
    private String email;
    private String telefone;

    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    
    public ClienteJuridicoResponseDTO() {
    }

    public ClienteJuridicoResponseDTO(Long id, String email, String telefone, String nomeFantasia, String razaoSocial,
            String cnpj) {
        this.id = id;
        this.email = email;
        this.telefone = telefone;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
    }

    public Long getId() {
        return id;
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
