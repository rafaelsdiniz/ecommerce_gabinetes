package br.repository;

import br.entity.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class PedidoRepository implements PanacheRepository<Pedido>{
    
}
