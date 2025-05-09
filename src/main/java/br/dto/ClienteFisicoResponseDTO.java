package br.dto;

public class ClienteFisicoResponseDTO {
    private Long id;
    private String email;
    private String telefone;
    private String nome;
    private String sobrenome;
    private String cpf;
    private Integer idade;

    public ClienteFisicoResponseDTO() {}

    public ClienteFisicoResponseDTO(Long id, String email, String telefone, String nome, String sobrenome, String cpf,
            Integer idade) {
        this.id = id;
        this.email = email;
        this.telefone = telefone;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.idade = idade;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
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

    


}
