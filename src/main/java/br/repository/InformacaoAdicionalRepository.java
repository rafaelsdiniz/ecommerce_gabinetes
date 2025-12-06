package br.repository;

import java.util.List;

import br.model.InformacaoAdicional;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InformacaoAdicionalRepository implements PanacheRepository<InformacaoAdicional> {
    
    public List<InformacaoAdicional> findByGabineteId(Long gabineteId) {
        return find("gabinete.id", gabineteId).list();
    }
}
