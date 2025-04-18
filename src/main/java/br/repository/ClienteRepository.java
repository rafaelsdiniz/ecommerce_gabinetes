package br.repository;

import br.entity.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped // fala pro quarkus que essa classe ele precisa gerenciar

public class ClienteRepository implements PanacheRepository<Cliente>{
    
}
