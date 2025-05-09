package br.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import br.dto.ItemPedidoRequestDTO;
import br.dto.ItemPedidoResponseDTO;
import br.dto.PedidoRequestDTO;
import br.dto.PedidoResponseDTO;
import br.model.Cliente;
import br.model.Gabinete;
import br.model.ItemPedido;
import br.model.Pedido;
import br.repository.ClienteFisicoRepository;
import br.repository.ClienteJuridicoRepository;
import br.repository.GabineteRepository;
import br.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoService {
    @Inject
    PedidoRepository pedidoRepository;
    @Inject
    GabineteRepository gabineteRepository;
    @Inject
    ClienteFisicoRepository clienteFisicoRepository;
    @Inject
    ClienteJuridicoRepository clienteJuridicoRepository;

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        Cliente cliente = buscarClientePorId(dto.getClienteId());
        
        pedido.setCliente(cliente);
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setStatus(dto.getStatus());
        pedido.setEndereco(dto.getEndereco());

        if (dto.getItens() != null) {
            List<ItemPedido> itens = dto.getItens().stream()
                .map(itemDto -> toItemPedido(itemDto, pedido))
                .collect(Collectors.toList());
            
            pedido.setItens(itens);
            pedido.setValorTotal(calcularValorTotal(itens));
        }

        pedidoRepository.persist(pedido);
        return toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }
        return toResponseDTO(pedido);
    }

    @Transactional
    public void atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        Cliente cliente = buscarClientePorId(dto.getClienteId());
        pedido.setCliente(cliente);
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setStatus(dto.getStatus());
        pedido.setEndereco(dto.getEndereco());

        if (dto.getItens() != null) {
            pedido.getItens().clear();
            List<ItemPedido> itens = dto.getItens().stream()
                .map(itemDto -> toItemPedido(itemDto, pedido))
                .collect(Collectors.toList());
            
            pedido.getItens().addAll(itens);
            pedido.setValorTotal(calcularValorTotal(itens));
        }
    }

    @Transactional
    public void deletar(Long id) {
        if (!pedidoRepository.deleteById(id)) {
            throw new NotFoundException("Pedido não encontrado para deletar.");
        }
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
            .map(this::toItemPedidoResponseDTO)
            .collect(Collectors.toList());
        
        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getCliente(),
            pedido.getDataPedido(),
            itensDTO,
            pedido.getValorTotal(),
            pedido.getStatus(),
            pedido.getEndereco()
        );
    }

    private ItemPedidoResponseDTO toItemPedidoResponseDTO(ItemPedido item) {
        return new ItemPedidoResponseDTO(
            item.getId(),
            item.getGabinete().getId(),
            item.getGabinete().getNomeExibicao(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getPrecoTotal()
        );
    }

    private ItemPedido toItemPedido(ItemPedidoRequestDTO dto, Pedido pedido) {
        Gabinete gabinete = gabineteRepository.findById(dto.getIdGabinete());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado com ID: " + dto.getIdGabinete());
        }

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setGabinete(gabinete);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());
        
        return item;
    }

    private BigDecimal calcularValorTotal(List<ItemPedido> itens) {
        return itens.stream()
            .map(ItemPedido::getPrecoTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Cliente buscarClientePorId(Long id) {
        Cliente cliente = clienteFisicoRepository.findById(id);
        if (cliente != null) return cliente;

        cliente = clienteJuridicoRepository.findById(id);
        if (cliente != null) return cliente;

        throw new NotFoundException("Cliente não encontrado com ID: " + id);
    }
}