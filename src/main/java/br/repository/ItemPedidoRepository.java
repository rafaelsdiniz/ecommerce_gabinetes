package br.repository;

import br.model.ItemPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class ItemPedidoRepository implements PanacheRepository<ItemPedido>{
    
}
