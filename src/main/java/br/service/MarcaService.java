package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.MarcaRequestDTO;
import br.dto.MarcaResponseDTO;
import br.entity.Marca;
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
    public void salvar(MarcaRequestDTO dto){
        Marca marca = new Marca();
        marca.setNome(dto.getNome());
        repository.persist(marca);
    }

    public List<MarcaResponseDTO> listarTodos(){
        return repository.listAll().stream()
            .map(marca -> new MarcaResponseDTO(
                marca.getId(),
                marca.getNome()))
            .collect(Collectors.toList()); 
    }

    public MarcaResponseDTO buscarPorId(long id) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada, 404");
        }
        return new MarcaResponseDTO(
            marca.getId(),
            marca.getNome());
    }

    @Transactional
    public void atualizar(long id, MarcaRequestDTO dto) {
        Marca marca = repository.findById(id);
        if(marca != null){
            marca.setNome(dto.getNome());
        }
    }

    @Transactional
    public void deletar(long id){
        repository.deleteById(id);
    }
}
