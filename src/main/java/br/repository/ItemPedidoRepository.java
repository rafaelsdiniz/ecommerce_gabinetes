package br.repository;

import java.util.List;

import br.model.ItemPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemPedidoRepository implements PanacheRepository<ItemPedido> {

    public List<ItemPedido> findByClienteId(Long clienteId) {
        return find("pedido.cliente.id", clienteId).list();
    }
}
