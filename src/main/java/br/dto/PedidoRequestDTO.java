package br.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.enums.StatusPedido;
import br.model.Endereco;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PedidoRequestDTO {

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Data do pedido é obrigatória")
    private LocalDateTime dataPedido;

    @NotEmpty(message = "Lista de itens não pode ser vazia")
    private List<ItemPedidoRequestDTO> itens;

    @NotNull(message = "Status é obrigatório")
    private StatusPedido status;

    @NotNull(message= "Endereço é obrigatório")
    private Endereco endereco;

    public PedidoRequestDTO() {}

    public PedidoRequestDTO(Long clienteId, LocalDateTime dataPedido, List<ItemPedidoRequestDTO> itens,
                            StatusPedido status, Endereco endereco) {
        this.clienteId = clienteId;
        this.dataPedido = dataPedido;
        this.itens = itens;
        this.status = status;
        this.endereco = endereco;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
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
