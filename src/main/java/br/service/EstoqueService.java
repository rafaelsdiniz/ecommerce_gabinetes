package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.request.EstoqueRequestDTO;
import br.dto.response.EstoqueResponseDTO;
import br.model.Estoque;
import br.model.Gabinete;
import br.repository.EstoqueRepository;
import br.repository.GabineteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class EstoqueService {

    @Inject
    EstoqueRepository estoqueRepository;

    @Inject
    GabineteRepository gabineteRepository;

    @Transactional
    public EstoqueResponseDTO salvar(EstoqueRequestDTO dto) {
        Gabinete gabinete = gabineteRepository.findById(dto.gabineteId());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado para o ID " + dto.gabineteId());
        }

        Estoque estoque = new Estoque();
        estoque.setGabinete(gabinete);
        estoque.setQuantidadeDisponivel(dto.quantidadeDisponivel());
        estoqueRepository.persist(estoque);

        return EstoqueResponseDTO.valueOf(estoque);
    }

    @Transactional
    public EstoqueResponseDTO adicionarEstoque(Long gabineteId, Integer quantidade) {
        if (quantidade <= 0) {
            throw new WebApplicationException("Quantidade deve ser maior que zero.", 400);
        }

        Estoque estoque = estoqueRepository.findByGabineteId(gabineteId)
            .orElseThrow(() -> new NotFoundException("Estoque não encontrado para o gabinete ID: " + gabineteId));

        estoque.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel() + quantidade);

        return EstoqueResponseDTO.valueOf(estoque);
    }

    @Transactional
    public EstoqueResponseDTO removerEstoque(Long gabineteId, Integer quantidade) {
        if (quantidade <= 0) {
            throw new WebApplicationException("Quantidade deve ser maior que zero.", 400);
        }

        Estoque estoque = estoqueRepository.findByGabineteId(gabineteId)
            .orElseThrow(() -> new NotFoundException("Estoque não encontrado para o gabinete ID: " + gabineteId));

        if (estoque.getQuantidadeDisponivel() < quantidade) {
            throw new WebApplicationException("Estoque insuficiente. Disponível: " + estoque.getQuantidadeDisponivel(), 400);
        }

        estoque.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel() - quantidade);

        return EstoqueResponseDTO.valueOf(estoque);
    }

    public boolean verificarDisponibilidade(Long gabineteId, Integer quantidadeDesejada) {
        Estoque estoque = estoqueRepository.findByGabineteId(gabineteId)
            .orElseThrow(() -> new NotFoundException("Estoque não encontrado para o gabinete ID: " + gabineteId));

        return estoque.getQuantidadeDisponivel() >= quantidadeDesejada;
    }

    public EstoqueResponseDTO buscarPorGabineteId(Long gabineteId) {
        Estoque estoque = estoqueRepository.findByGabineteId(gabineteId)
            .orElseThrow(() -> new NotFoundException("Estoque não encontrado para o gabinete ID: " + gabineteId));

        return EstoqueResponseDTO.valueOf(estoque);
    }

    public List<EstoqueResponseDTO> listarEstoqueBaixo(Integer quantidadeMinima) {
        return estoqueRepository.findEstoqueBaixo(quantidadeMinima).stream()
            .map(EstoqueResponseDTO::valueOf)
            .collect(Collectors.toList());
    }

    public List<EstoqueResponseDTO> listarTodos() {
        return estoqueRepository.listAll().stream()
            .map(EstoqueResponseDTO::valueOf)
            .collect(Collectors.toList());
    }

    public EstoqueResponseDTO buscarPorId(Long id) {
        Estoque estoque = estoqueRepository.findById(id);
        if (estoque == null) {
            throw new NotFoundException("Estoque não encontrado, 404");
        }

        return EstoqueResponseDTO.valueOf(estoque);
    }

    @Transactional
    public EstoqueResponseDTO atualizar(Long id, EstoqueRequestDTO dto) {
        Estoque estoque = estoqueRepository.findById(id);
        if (estoque == null) {
            throw new NotFoundException("Estoque não encontrado, 404");
        }

        Gabinete gabinete = gabineteRepository.findById(dto.gabineteId());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado para o ID " + dto.gabineteId());
        }

        estoque.setGabinete(gabinete);
        estoque.setQuantidadeDisponivel(dto.quantidadeDisponivel());

        return EstoqueResponseDTO.valueOf(estoque);
    }

    @Transactional
    public void deletar(Long id) {
        if (!estoqueRepository.deleteById(id)) {
            throw new NotFoundException("Estoque não encontrado, 404");
        }
    }
}
