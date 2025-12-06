package br.repository;

import java.util.List;

import br.model.Pedido;
import br.model.enums.StatusPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public List<Pedido> findByClienteId(Long clienteId) {
        return list("cliente.id", clienteId);
    }

    public List<Pedido> findByStatus(StatusPedido status) {
        return list("status", status);
    }

    public List<Pedido> findByClienteIdAndStatus(Long clienteId, StatusPedido status) {
        return list("cliente.id = ?1 and status = ?2", clienteId, status);
    }
}
