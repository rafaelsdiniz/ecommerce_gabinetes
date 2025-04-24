package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.PagamentoRequestDTO;
import br.dto.PagamentoResponseDTO;
import br.entity.Pagamento;
import br.entity.Pedido;
import br.repository.PagamentoRepository;
import br.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PagamentoService {

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Transactional
    public void salvar(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(dto.getFormaPagamento());
        pagamento.setStatusPagamento(dto.getStatusPagamento());
        pagamento.setValor(dto.getValor());
        pagamento.setData(dto.getData());

        pagamentoRepository.persist(pagamento);
    }

    public List<PagamentoResponseDTO> listarTodos() {
        return pagamentoRepository.listAll().stream()
            .map(pagamento -> new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getPedido().getId(),
                pagamento.getFormaPagamento(),
                pagamento.getStatusPagamento(),
                pagamento.getValor(),
                pagamento.getData()))
            .collect(Collectors.toList());
    }

    public PagamentoResponseDTO buscarPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }

        return new PagamentoResponseDTO(
            pagamento.getId(),
            pagamento.getPedido().getId(),
            pagamento.getFormaPagamento(),
            pagamento.getStatusPagamento(),
            pagamento.getValor(),
            pagamento.getData());
    }

    @Transactional
    public void atualizar(Long id, PagamentoRequestDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(dto.getFormaPagamento());
        pagamento.setStatusPagamento(dto.getStatusPagamento());
        pagamento.setValor(dto.getValor());
        pagamento.setData(dto.getData());
    }

    @Transactional
    public void deletar(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
