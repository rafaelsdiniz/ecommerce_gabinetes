package br.dto;

public class ClienteResponseDTO {
    private long id;
    private String nome;
    private String ultimoNome;
    private String idade;
    private String email;

    public ClienteResponseDTO(long id, String nome, String ultimoNome, String idade, String email) {
        this.id = id;
        this.nome = nome;
        this.ultimoNome = ultimoNome;
        this.idade = idade;
        this.email = email;
    }

    public ClienteResponseDTO() {
    }

    public long getId() {
        return id;
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
