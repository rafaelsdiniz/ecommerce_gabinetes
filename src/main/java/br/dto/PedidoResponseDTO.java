package br.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.entity.Cliente;
import br.entity.ItemPedido;
import br.enums.StatusPedido;

public class PedidoResponseDTO {
    private long id;
    private Cliente cliente;
    private LocalDateTime dataPedido;
    private List<ItemPedido> itens;
    private double valorTotal;
    private StatusPedido status;
    
    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(long id, Cliente cliente, LocalDateTime dataPedido, List<ItemPedido> itens,
            double valorTotal, StatusPedido status) {
        this.id = id;
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

}
