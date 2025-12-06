package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.request.MarcaRequestDTO;
import br.dto.response.MarcaResponseDTO;
import br.model.Marca;
import br.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MarcaService {

    @Inject
    MarcaRepository repository;

    @Transactional
    public MarcaResponseDTO salvar(MarcaRequestDTO dto) {
        Marca marca = new Marca();
        marca.setNome(dto.nome());
        repository.persist(marca);

        return new MarcaResponseDTO(
            marca.getId(),
            marca.getNome(),
            marca.getDescricao()
        );
    }

    public List<MarcaResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(marca -> new MarcaResponseDTO(
                marca.getId(),
                marca.getNome(),
                marca.getDescricao()
            ))
            .collect(Collectors.toList());
    }

    public MarcaResponseDTO buscarPorId(Long id) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada.");
        }

        return new MarcaResponseDTO(
            marca.getId(),
            marca.getNome(),
            marca.getDescricao()
        );
    }

    @Transactional
    public MarcaResponseDTO atualizar(Long id, MarcaRequestDTO dto) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada.");
        }

        marca.setNome(dto.nome());
        marca.setDescricao(dto.descricao());

        return new MarcaResponseDTO(
            marca.getId(),
            marca.getNome(),
            marca.getDescricao()
        );
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Marca não encontrada para exclusão.");
        }
    }
}
