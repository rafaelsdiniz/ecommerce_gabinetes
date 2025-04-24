package br.repository;

import br.entity.ClienteJuridico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped // fala pro quarkus que essa classe ele precisa gerenciar

public class ClienteJuridicoRepository implements PanacheRepository<ClienteJuridico>{
    
}
