package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.PedidoRequestDTO;
import br.dto.PedidoResponseDTO;
import br.entity.Pedido;
import br.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoService {
    
    @Inject
    PedidoRepository repository;

    @Transactional
    public void salvar(PedidoRequestDTO dto){
        Pedido pedido = new Pedido();
        pedido.setCliente(dto.getCliente());
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setItens(dto.getItens());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setStatus(dto.getStatus());
        repository.persist(pedido);
    }

    public List<PedidoResponseDTO> listarTodos(){
        return repository.listAll().stream()
            .map(pedido -> new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCliente(),
                pedido.getDataPedido(),
                pedido.getItens(),
                pedido.getValorTotal(),
                pedido.getStatus()))
            .collect(Collectors.toList());
    }

        public PedidoResponseDTO buscarPorId(long id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrada, 404");
        }
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCliente(),
                pedido.getDataPedido(),
                pedido.getItens(),
                pedido.getValorTotal(),
                pedido.getStatus());
    }

    @Transactional
    public void atualizar(long id, PedidoRequestDTO dto) {
        Pedido pedido = repository.findById(id);
        if(pedido != null){
            pedido.setCliente(dto.getCliente());
            pedido.setDataPedido(dto.getDataPedido());
            pedido.setItens(dto.getItens());
            pedido.setValorTotal(dto.getValorTotal());
            pedido.setStatus(dto.getStatus());
        }
    }

    @Transactional
    public void deletar(long id){
        repository.deleteById(id);
    }
}
