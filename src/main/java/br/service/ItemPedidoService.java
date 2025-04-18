package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.ItemPedidoRequestDTO;
import br.dto.ItemPedidoResponseDTO;
import br.entity.ItemPedido;
import br.repository.ItemPedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemPedidoService {
    
    @Inject
    ItemPedidoRepository repository;

    @Transactional
    public void salvar(ItemPedidoRequestDTO dto){
        ItemPedido ItemPedido = new ItemPedido();
        ItemPedido.setPedido(dto.getPedido());
        ItemPedido.setGabinete(dto.getGabinete());
        ItemPedido.setQuantidade(dto.getQuantidade());
        ItemPedido.setPrecoUnitario(dto.getPrecoUnitario());
        ItemPedido.setPrecoTotal(dto.getPrecoTotal());
        repository.persist(ItemPedido);
    }

    public List<ItemPedidoResponseDTO> listarTodos(){
        return repository.listAll().stream()
            .map(ItemPedido -> new ItemPedidoResponseDTO(
                ItemPedido.getId(),
                ItemPedido.getPedido(),
                ItemPedido.getGabinete(),
                ItemPedido.getQuantidade(),
                ItemPedido.getPrecoUnitario(),
                ItemPedido.getPrecoTotal()))
            .collect(Collectors.toList());
    }

        public ItemPedidoResponseDTO buscarPorId(long id) {
        ItemPedido ItemPedido = repository.findById(id);
        if (ItemPedido == null) {
            throw new NotFoundException("ItemPedido não encontrada, 404");
        }
        return new ItemPedidoResponseDTO(
            ItemPedido.getId(),
            ItemPedido.getPedido(),
            ItemPedido.getGabinete(),
            ItemPedido.getQuantidade(),
            ItemPedido.getPrecoUnitario(),
            ItemPedido.getPrecoTotal());
    }

    @Transactional
    public void atualizar(long id, ItemPedidoRequestDTO dto) {
        ItemPedido ItemPedido = repository.findById(id);
        if(ItemPedido != null){
            ItemPedido.setPedido(dto.getPedido());
        ItemPedido.setGabinete(dto.getGabinete());
        ItemPedido.setQuantidade(dto.getQuantidade());
        ItemPedido.setPrecoUnitario(dto.getPrecoUnitario());
        ItemPedido.setPrecoTotal(dto.getPrecoTotal());
        }
    }

    @Transactional
    public void deletar(long id){
        repository.deleteById(id);
    }
}
