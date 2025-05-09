package br.repository;

import br.model.ClienteJuridico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteJuridicoRepository implements PanacheRepository<ClienteJuridico> {

}