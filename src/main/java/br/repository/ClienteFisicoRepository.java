package br.repository;

import br.model.ClienteFisico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteFisicoRepository implements PanacheRepository<ClienteFisico> {
    // Nenhum método por login necessário aqui
}
