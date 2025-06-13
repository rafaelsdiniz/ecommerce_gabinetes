package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.EstoqueRequestDTO;
import br.dto.EstoqueResponseDTO;
import br.model.Estoque;
import br.model.Gabinete;
import br.repository.EstoqueRepository;
import br.repository.GabineteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstoqueService {

    @Inject
    EstoqueRepository estoqueRepository;

    @Inject
    GabineteRepository gabineteRepository;

    @Transactional
    public EstoqueResponseDTO salvar(EstoqueRequestDTO dto) {
        Gabinete gabinete = gabineteRepository.findById(dto.getGabineteId());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado para o ID " + dto.getGabineteId());
        }

        Estoque estoque = new Estoque();
        estoque.setGabinete(gabinete);
        estoque.setQuantidadeDisponivel(dto.getQuantidadeDisponivel());
        estoqueRepository.persist(estoque);

        return new EstoqueResponseDTO(
            estoque.getId(),
            gabinete.getId(),
            gabinete.getNomeExibicao(),
            estoque.getQuantidadeDisponivel()
        );
    }

    public List<EstoqueResponseDTO> listarTodos() {
        return estoqueRepository.listAll().stream()
            .map(estoque -> new EstoqueResponseDTO(
                estoque.getId(),
                estoque.getGabinete().getId(),
                estoque.getGabinete().getNomeExibicao(),
                estoque.getQuantidadeDisponivel()))
            .collect(Collectors.toList());
    }

    public EstoqueResponseDTO buscarPorId(Long id) {
        Estoque estoque = estoqueRepository.findById(id);
        if (estoque == null) {
            throw new NotFoundException("Estoque não encontrado, 404");
        }

        return new EstoqueResponseDTO(
            estoque.getId(),
            estoque.getGabinete().getId(),
            estoque.getGabinete().getNomeExibicao(),
            estoque.getQuantidadeDisponivel());
    }

    @Transactional
    public EstoqueResponseDTO atualizar(Long id, EstoqueRequestDTO dto) {
        Estoque estoque = estoqueRepository.findById(id);
        if (estoque == null) {
            throw new NotFoundException("Estoque não encontrado, 404");
        }

        Gabinete gabinete = gabineteRepository.findById(dto.getGabineteId());
        if (gabinete == null) {
            throw new NotFoundException("Gabinete não encontrado para o ID " + dto.getGabineteId());
        }

        estoque.setGabinete(gabinete);
        estoque.setQuantidadeDisponivel(dto.getQuantidadeDisponivel());

        return new EstoqueResponseDTO(
            estoque.getId(),
            gabinete.getId(),
            gabinete.getNomeExibicao(),
            estoque.getQuantidadeDisponivel()
        );
    }

    @Transactional
    public void deletar(Long id) {
        if (!estoqueRepository.deleteById(id)) {
            throw new NotFoundException("Estoque não encontrado, 404");
        }
    }
}