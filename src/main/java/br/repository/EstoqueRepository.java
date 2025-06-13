package br.repository;

import java.util.Optional;

import br.model.Estoque;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstoqueRepository implements PanacheRepository<Estoque> {

    public Optional<Estoque> findByGabineteId(Long gabineteId) {
        return find("gabinete.id", gabineteId).firstResultOptional();
    }
}
