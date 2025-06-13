package br.repository;

import java.util.Optional;

import br.model.ClienteJuridico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteJuridicoRepository implements PanacheRepository<ClienteJuridico> {
    public Optional<ClienteJuridico> findByLogin(String login) {
        return find("login", login).firstResultOptional();
    }
}
