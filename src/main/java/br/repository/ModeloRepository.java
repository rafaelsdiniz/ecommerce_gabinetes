package br.repository;

import java.util.List;

import br.model.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {
    
    public List<Modelo> findByMarcaId(Long marcaId) {
        return find("marca.id", marcaId).list();
    }
    
    public List<Modelo> findByNomeModelo(String nome) {
        return find("LOWER(nomeModelo) LIKE LOWER(?1)", "%" + nome + "%").list();
    }
}
