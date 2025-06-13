
package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.ItemPedidoRequestDTO;
import br.dto.ItemPedidoResponseDTO;
import br.model.Cliente;
import br.model.Gabinete;
import br.model.ItemPedido;
import br.model.Pedido;
import br.repository.GabineteRepository;
import br.repository.ItemPedidoRepository;
import br.repository.PedidoRepository;
import br.repository.UsuarioRepository;
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
    UsuarioRepository usuarioRepository;

    @Inject
    SecurityIdentity securityIdentity;

    @Transactional
    public ItemPedidoResponseDTO criar(ItemPedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getIdPedido());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        Gabinete gabinete = gabineteRepository.findById(dto.getIdGabinete());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado.");
        }

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setGabinete(gabinete);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());

        itemPedidoRepository.persist(item);

        return toResponseDTO(item);
    }

    public List<ItemPedidoResponseDTO> listarTodos() {
        if (securityIdentity.hasRole("Adm")) {
            return itemPedidoRepository.listAll().stream()
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
        } else {
            return listarPorClienteLogado();
        }
    }

    public List<ItemPedidoResponseDTO> listarPorClienteLogado() {
        Cliente cliente = getClienteLogado();
        // Assumindo que ItemPedidoRepository tenha método para buscar por cliente via pedido
        return itemPedidoRepository.findByClienteId(cliente.getId()).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private Cliente getClienteLogado() {
        String username = securityIdentity.getPrincipal().getName();
        return usuarioRepository.getClienteByUsername(username)
                .orElseThrow(() -> new NotFoundException("Cliente com login '" + username + "' não encontrado."));
    }

    public ItemPedidoResponseDTO buscarPorId(Long id) {
        ItemPedido item = itemPedidoRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item do pedido não encontrado.");
        }
        return toResponseDTO(item);
    }

    @Transactional
    public void atualizar(Long id, ItemPedidoRequestDTO dto) {
        ItemPedido item = itemPedidoRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item do pedido não encontrado.");
        }

        Pedido pedido = pedidoRepository.findById(dto.getIdPedido());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        Gabinete gabinete = gabineteRepository.findById(dto.getIdGabinete());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado.");
        }

        item.setPedido(pedido);
        item.setGabinete(gabinete);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());
    }

    @Transactional
    public void deletar(Long id) {
        if (!itemPedidoRepository.deleteById(id)) {
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
