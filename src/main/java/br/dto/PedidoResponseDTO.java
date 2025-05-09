package br.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import br.enums.StatusPedido;
import br.model.Cliente;
import br.model.Endereco;

public class PedidoResponseDTO {
    private Long id;
    private Cliente cliente;
    private LocalDateTime dataPedido;
    private List<ItemPedidoResponseDTO> itens;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private Endereco endereco;

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(Long id, Cliente cliente, LocalDateTime dataPedido, 
                           List<ItemPedidoResponseDTO> itens, BigDecimal valorTotal, 
                           StatusPedido status, Endereco endereco) {
        this.id = id;
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.status = status;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ItemPedidoResponseDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoResponseDTO> itens) {
        this.itens = itens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}