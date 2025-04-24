package br.repository;

import br.entity.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class EnderecoRepository implements PanacheRepository<Endereco>{
    
}
