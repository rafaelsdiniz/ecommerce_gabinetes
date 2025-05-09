package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.ItemPedidoRequestDTO;
import br.dto.ItemPedidoResponseDTO;
import br.model.ItemPedido;
import br.repository.GabineteRepository;
import br.repository.ItemPedidoRepository;
import br.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemPedidoService {

    @Inject
    ItemPedidoRepository itemRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    GabineteRepository gabineteRepository;

    @Transactional
    public ItemPedidoResponseDTO criar(ItemPedidoRequestDTO dto) {
        var pedido = pedidoRepository.findById(dto.getIdPedido());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        var gabinete = gabineteRepository.findById(dto.getIdGabinete());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado.");
        }

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setGabinete(gabinete);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());
        // O precoTotal será calculado automaticamente pelo @PrePersist

        itemRepository.persist(item);

        return new ItemPedidoResponseDTO(
            item.getId(),
            item.getGabinete().getId(),
            item.getGabinete().getNomeExibicao(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getPrecoTotal()
        );
    }

    public List<ItemPedidoResponseDTO> listarTodos() {
        return itemRepository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemPedidoResponseDTO buscarPorId(Long id) {
        ItemPedido item = itemRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item do pedido não encontrado.");
        }
        return toResponseDTO(item);
    }

    @Transactional
    public void atualizar(Long id, ItemPedidoRequestDTO dto) {
        ItemPedido item = itemRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item do pedido não encontrado.");
        }

        var pedido = pedidoRepository.findById(dto.getIdPedido());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        var gabinete = gabineteRepository.findById(dto.getIdGabinete());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado.");
        }

        item.setPedido(pedido);
        item.setGabinete(gabinete);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());
        // O precoTotal será atualizado automaticamente pelo @PreUpdate
    }

    @Transactional
    public void deletar(Long id) {
        if (!itemRepository.deleteById(id)) {
            throw new NotFoundException("Item do pedido não encontrado para exclusão.");
        }
    }

    private ItemPedidoResponseDTO toResponseDTO(ItemPedido item) {
        return new ItemPedidoResponseDTO(
            item.getId(),
            item.getGabinete().getId(),
            item.getGabinete().getNomeExibicao(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getPrecoTotal()
        );
    }
}