package br.repository;

import br.entity.Pagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class PagamentoRepository implements PanacheRepository<Pagamento>{
    
}
