package br.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.entity.Cliente;
import br.entity.ItemPedido;
import br.enums.StatusPedido;

public class PedidoRequestDTO {
    private Cliente cliente;
    private LocalDateTime dataPedido;
    private List<ItemPedido> itens;
    private double valorTotal;
    private StatusPedido status;
    
    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(Cliente cliente, LocalDateTime dataPedido, List<ItemPedido> itens, double valorTotal,
            StatusPedido status) {
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.status = status;
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
