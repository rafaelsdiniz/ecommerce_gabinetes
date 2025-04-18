package br.repository;

import br.entity.Marca;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class MarcaRepository implements PanacheRepository<Marca>{

}
