package br.dto;

public class ClienteRequestDTO {
    private String nome;
    private String ultimoNome;
    private String idade;
    private String email;

    public ClienteRequestDTO() {
    }

    public ClienteRequestDTO(String email, String idade, String nome, String ultimoNome) {
        this.email = email;
        this.idade = idade;
        this.nome = nome;
        this.ultimoNome = ultimoNome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
