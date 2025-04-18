package br.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity// entenda que faz ligação com o bd, que é uma tabela

public class Cliente {
    @Id // vai dizer pro banco que o id será chave primaria
    @GeneratedValue // vai dizer pro bd que é autoincrement

    private long id;
    private String nome;
    private String ultimoNome;
    private String idade;
    private String email;

    public Cliente() {
    }

    public Cliente(String email, long id, String idade, String nome, String ultimoNome) {
        this.email = email;
        this.id = id;
        this.idade = idade;
        this.nome = nome;
        this.ultimoNome = ultimoNome;
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

