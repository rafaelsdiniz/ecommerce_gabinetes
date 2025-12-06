package br.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.dto.request.EnderecoRequestDTO;
import br.dto.request.ItemPedidoRequestDTO;
import br.dto.request.PedidoRequestDTO;
import br.dto.response.ClienteResponseDTO;
import br.dto.response.ItemPedidoResponseDTO;
import br.dto.response.PedidoResponseDTO;
import br.model.Cliente;
import br.model.Endereco;
import br.model.Estoque;
import br.model.Gabinete;
import br.model.ItemPedido;
import br.model.Pedido;
import br.model.enums.StatusPedido;
import br.repository.ClienteRepository;
import br.repository.EstoqueRepository;
import br.repository.GabineteRepository;
import br.repository.PedidoRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import org.jboss.logging.Logger;

@ApplicationScoped
public class PedidoService {

    private static final Logger LOG = Logger.getLogger(PedidoService.class);

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    GabineteRepository gabineteRepository;

    @Inject
    EstoqueRepository estoqueRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    SecurityIdentity securityIdentity;

    // -----------------------------------------------------------------------------
    // CRIAR PEDIDO
    // -----------------------------------------------------------------------------
    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Cliente cliente = buscarClienteLogado();

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setEndereco(toEnderecoEntity(dto.endereco()));

        if (dto.itens() != null && !dto.itens().isEmpty()) {
            List<ItemPedido> itens = dto.itens().stream()
                    .map(itemDto -> toItemPedido(itemDto, pedido))
                    .collect(Collectors.toList());
            pedido.setItens(itens);
            pedido.setValorTotal(calcularValorTotal(itens));
        } else {
            throw new WebApplicationException("Lista de itens não pode ser vazia.", 400);
        }

        pedidoRepository.persist(pedido);
        LOG.infof("Pedido criado (cliente=%s, id=%d)", cliente.getEmail(), pedido.getId());
        return toResponseDTO(pedido);
    }

    // -----------------------------------------------------------------------------
    // ATUALIZAR
    // -----------------------------------------------------------------------------
    @Transactional
    public void atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null)
            throw new NotFoundException("Pedido não encontrado.");

        validarClientePodeAcessar(pedido);

        pedido.setEndereco(toEnderecoEntity(dto.endereco()));

        if (dto.itens() != null) {
            List<ItemPedido> itens = dto.itens().stream()
                    .map(itemDto -> toItemPedido(itemDto, pedido))
                    .collect(Collectors.toList());
            pedido.setItens(itens);
            pedido.setValorTotal(calcularValorTotal(itens));
        }

        pedidoRepository.persist(pedido);
    }

    // -----------------------------------------------------------------------------
    // FINALIZAR
    // -----------------------------------------------------------------------------
    @Transactional
    public PedidoResponseDTO finalizarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null)
            throw new NotFoundException("Pedido não encontrado.");

        validarClientePodeAcessar(pedido);

        if (pedido.getStatus() == StatusPedido.CANCELADO)
            throw new WebApplicationException("Não é possível finalizar um pedido cancelado.", 400);

        if (pedido.getStatus() == StatusPedido.ENTREGUE)
            throw new WebApplicationException("Pedido já foi entregue.", 400);

        for (ItemPedido item : pedido.getItens()) {
            Estoque estoque = estoqueRepository.findByGabineteId(item.getGabinete().getId())
                    .orElseThrow(() -> new NotFoundException(
                            "Estoque não encontrado para o gabinete ID: " + item.getGabinete().getId()));

            if (estoque.getQuantidadeDisponivel() < item.getQuantidade())
                throw new WebApplicationException("Estoque insuficiente para o gabinete: "
                        + item.getGabinete().getNomeExibicao(), 400);
        }

        pedido.setStatus(StatusPedido.PROCESSANDO);
        LOG.infof("Pedido %d movido para PROCESSANDO", pedido.getId());
        return toResponseDTO(pedido);
    }

    // -----------------------------------------------------------------------------
    // CANCELAR
    // -----------------------------------------------------------------------------
    @Transactional
    public PedidoResponseDTO cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null)
            throw new NotFoundException("Pedido não encontrado.");

        validarClientePodeAcessar(pedido);

        if (pedido.getStatus() == StatusPedido.ENTREGUE)
            throw new WebApplicationException("Não é possível cancelar um pedido já entregue.", 400);

        if (pedido.getStatus() == StatusPedido.CANCELADO)
            throw new WebApplicationException("Pedido já está cancelado.", 400);

        for (ItemPedido item : pedido.getItens()) {
            Estoque estoque = estoqueRepository.findByGabineteId(item.getGabinete().getId())
                    .orElseThrow(() -> new NotFoundException(
                            "Estoque não encontrado para o gabinete ID: " + item.getGabinete().getId()));
            estoque.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel() + item.getQuantidade());
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        LOG.infof("Pedido %d cancelado", pedido.getId());
        return toResponseDTO(pedido);
    }

    // -----------------------------------------------------------------------------
    // STATUS (ADMIN)
    // -----------------------------------------------------------------------------
    @Transactional
    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null)
            throw new NotFoundException("Pedido não encontrado.");

        pedido.setStatus(novoStatus);
        return toResponseDTO(pedido);
    }

    // -----------------------------------------------------------------------------
    // DELETAR (ADMIN)
    // -----------------------------------------------------------------------------
    @Transactional
    public void deletar(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null)
            throw new NotFoundException("Pedido não encontrado.");
        pedidoRepository.delete(pedido);
    }

    // -----------------------------------------------------------------------------
    // BUSCAS
    // -----------------------------------------------------------------------------
    public List<PedidoResponseDTO> buscarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> buscarHistoricoCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> listarTodos() {
        if (securityIdentity.hasRole("ADMIN")) {
            return pedidoRepository.listAll().stream()
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
        }
        return listarPorClienteLogado();
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null)
            throw new NotFoundException("Pedido não encontrado.");

        validarClientePodeAcessar(pedido);
        return toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listarPorClienteLogado() {
        Long clienteId = getClienteIdLogado();
        return buscarHistoricoCliente(clienteId);
    }

    // -----------------------------------------------------------------------------
    // NOVO MÉTODO: LISTAR POR CLIENTE (para uso no Resource)
    // -----------------------------------------------------------------------------
    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        return buscarHistoricoCliente(clienteId);
    }

    // -----------------------------------------------------------------------------
    // MÉTODOS AUXILIARES
    // -----------------------------------------------------------------------------
    private Cliente buscarClienteLogado() {
        String email = securityIdentity.getPrincipal().getName();
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Cliente com email '" + email + "' não encontrado"));
    }

    private Long getClienteIdLogado() {
        return buscarClienteLogado().getId();
    }

    private void validarClientePodeAcessar(Pedido pedido) {
        if (securityIdentity.hasRole("ADMIN")) return;

        Long clienteLogado = getClienteIdLogado();
        if (!pedido.getCliente().getId().equals(clienteLogado))
            throw new WebApplicationException("Você não tem permissão para acessar este pedido", 403);
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
                .map(this::toItemPedidoResponseDTO)
                .collect(Collectors.toList());

        Cliente c = pedido.getCliente();
        ClienteResponseDTO clienteDTO = new ClienteResponseDTO(
                c.getId(),
                c.getNome(),
                c.getEmail(),
                c.getTelefone(),
                c.getCpf(),
                c.getPerfil()
        );

        return new PedidoResponseDTO(
                pedido.getId(),
                clienteDTO,
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
        Gabinete gabinete = gabineteRepository.findById(dto.idGabinete());
        if (gabinete == null)
            throw new NotFoundException("Gabinete não encontrado com ID: " + dto.idGabinete());

        Estoque estoque = estoqueRepository.findByGabineteId(gabinete.getId())
                .orElseThrow(() -> new NotFoundException(
                        "Estoque não encontrado para o gabinete ID: " + gabinete.getId()));

        if (dto.quantidade() > estoque.getQuantidadeDisponivel())
            throw new WebApplicationException(
                    "Estoque insuficiente para o gabinete: " + gabinete.getNomeExibicao(), 400);

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setGabinete(gabinete);
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnitario(BigDecimal.valueOf(gabinete.getPreco()));

        return item;
    }

    private BigDecimal calcularValorTotal(List<ItemPedido> itens) {
        return itens.stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Endereco toEnderecoEntity(EnderecoRequestDTO dto) {
        if (dto == null) return null;
        Endereco endereco = new Endereco();
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        return endereco;
    }
}