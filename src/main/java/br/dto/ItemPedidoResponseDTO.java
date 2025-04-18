package br.dto;

import br.entity.Gabinete;
import br.entity.Pedido;

public class ItemPedidoResponseDTO {
    private long id;
    private Pedido pedido;
    private Gabinete gabinete;
    private int quantidade;
    private double precoUnitario;
    private double precoTotal;
    
    public ItemPedidoResponseDTO() {
    }

    public ItemPedidoResponseDTO(long id, Pedido pedido, Gabinete gabinete, int quantidade, double precoUnitario,
            double precoTotal) {
        this.id = id;
        this.pedido = pedido;
        this.gabinete = gabinete;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.precoTotal = precoTotal;
    }

    public long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Gabinete getGabinete() {
        return gabinete;
    }

    public void setGabinete(Gabinete gabinete) {
        this.gabinete = gabinete;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    
}
