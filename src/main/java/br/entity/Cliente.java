package br.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity // Define que a classe é uma entidade JPA
@Inheritance(strategy = InheritanceType.JOINED) // Define que o relacionamento entre as classes será de herança (tabela separada para ClienteFisico e ClienteJuridico)
public abstract class Cliente {
    
    @Id // Define o ID como chave primária
    @GeneratedValue // Define que o valor do ID será gerado automaticamente pelo banco
    private long id;
    
    private String telefone;
    
    @ManyToOne // Define um relacionamento muitos-para-um com a classe Endereco
    @JoinColumn(name = "endereco_id") // Define o nome da coluna que vai referenciar a tabela de Endereços
    private Endereco endereco;
    
    private String email;

    // Construtores, getters e setters

    public Cliente() {
    }

    public Cliente(long id, String telefone, Endereco endereco, String email) {
        this.id = id;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
    }

    public long getId() {
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
