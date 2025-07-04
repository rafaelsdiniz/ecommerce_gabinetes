package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.CategoriaRequestDTO;
import br.dto.CategoriaResponseDTO;
import br.model.Categoria;
import br.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CategoriaService {

    @Inject
    CategoriaRepository repository;

    

    @Transactional
    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        repository.persist(categoria);

        return new CategoriaResponseDTO(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao()
        );
    }

    public List<CategoriaResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(categoria -> new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()))
            .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = repository.findById(id);
        if (categoria == null) {
            throw new NotFoundException("Categoria não encontrada, 404");
        }
        return new CategoriaResponseDTO(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao());
    }

    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = repository.findById(id);
        if (categoria != null) {
            categoria.setNome(dto.getNome());
            categoria.setDescricao(dto.getDescricao());
            return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
            );
        } else {
            throw new NotFoundException("Categoria não encontrada, 404");
        }
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Categoria não encontrada, 404");
        }
    }
}