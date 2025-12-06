package br.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import br.dto.request.ItemPedidoRequestDTO;
import br.dto.response.ItemPedidoResponseDTO;
import br.model.Cliente;
import br.model.Gabinete;
import br.model.ItemPedido;
import br.model.Pedido;
import br.repository.ClienteRepository;
import br.repository.GabineteRepository;
import br.repository.ItemPedidoRepository;
import br.repository.PedidoRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemPedidoService {

    @Inject
    ItemPedidoRepository itemPedidoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    GabineteRepository gabineteRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    SecurityIdentity securityIdentity;

    // ============================================================
    // CRIAR ITEM PEDIDO
    // ============================================================
    @Transactional
    public ItemPedidoResponseDTO criar(ItemPedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.idPedido());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        Gabinete gabinete = gabineteRepository.findById(dto.idGabinete());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado.");
        }

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setGabinete(gabinete);
        item.setQuantidade(dto.quantidade());
        // O preço unitário deve vir do gabinete, não do DTO
        item.setPrecoUnitario(BigDecimal.valueOf(gabinete.getPreco()));

        itemPedidoRepository.persist(item);

        return toResponseDTO(item);
    }

    // ============================================================
    // LISTAR TODOS OS ITENS
    // ============================================================
    public List<ItemPedidoResponseDTO> listarTodos() {
        if (securityIdentity.hasRole("ADMIN")) {
            return itemPedidoRepository.listAll()
                    .stream()
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
        }
        return listarPorClienteLogado();
    }

    public List<ItemPedidoResponseDTO> listarPorClienteLogado() {
        Cliente cliente = getClienteLogado();

        return itemPedidoRepository.findByClienteId(cliente.getId())
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // BUSCAR ITEM POR ID
    // ============================================================
    public ItemPedidoResponseDTO buscarPorId(Long id) {
        ItemPedido item = itemPedidoRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item do pedido não encontrado.");
        }
        return toResponseDTO(item);
    }

    // ============================================================
    // ATUALIZAR ITEM PEDIDO
    // ============================================================
    @Transactional
    public ItemPedidoResponseDTO atualizar(Long id, ItemPedidoRequestDTO dto) {
        ItemPedido item = itemPedidoRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item do pedido não encontrado.");
        }

        Pedido pedido = pedidoRepository.findById(dto.idPedido());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        Gabinete gabinete = gabineteRepository.findById(dto.idGabinete());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado.");
        }

        item.setPedido(pedido);
        item.setGabinete(gabinete);
        item.setQuantidade(dto.quantidade());
        // O preço unitário deve vir do gabinete, não do DTO
        item.setPrecoUnitario(BigDecimal.valueOf(gabinete.getPreco()));

        return toResponseDTO(item);
    }

    // ============================================================
    // DELETAR ITEM PEDIDO
    // ============================================================
    @Transactional
    public void deletar(Long id) {
        if (!itemPedidoRepository.deleteById(id)) {
            throw new NotFoundException("Item do pedido não encontrado para exclusão.");
        }
    }

    // ============================================================
    // MÉTODO AUXILIAR: BUSCA CLIENTE LOGADO
    // ============================================================
    private Cliente getClienteLogado() {
        String email = securityIdentity.getPrincipal().getName();
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        "Cliente com email '" + email + "' não encontrado."
                ));
    }

    // ============================================================
    // CONVERSÃO ENTITY → DTO
    // ============================================================
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