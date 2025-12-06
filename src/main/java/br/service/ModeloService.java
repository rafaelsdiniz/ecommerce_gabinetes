package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.request.ModeloRequestDTO;
import br.dto.response.ModeloResponseDTO;
import br.model.Marca;
import br.model.Modelo;
import br.repository.MarcaRepository;
import br.repository.ModeloRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ModeloService {

    @Inject
    ModeloRepository modeloRepository;
    
    @Inject
    MarcaRepository marcaRepository;

    public List<ModeloResponseDTO> findAll() {
        return modeloRepository.listAll().stream()
                .map(ModeloResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public ModeloResponseDTO findById(Long id) {
        Modelo modelo = modeloRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Modelo não encontrado"));
        return ModeloResponseDTO.valueOf(modelo);
    }

    @Transactional
    public ModeloResponseDTO create(ModeloRequestDTO dto) {
        Marca marca = marcaRepository.findByIdOptional(dto.marcaId())
                .orElseThrow(() -> new NotFoundException("Marca não encontrada"));

        Modelo modelo = new Modelo(dto.nomeModelo(), marca);
        modeloRepository.persist(modelo);
        
        return ModeloResponseDTO.valueOf(modelo);
    }

    @Transactional
    public ModeloResponseDTO update(Long id, ModeloRequestDTO dto) {
        Modelo modelo = modeloRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Modelo não encontrado"));

        Marca marca = marcaRepository.findByIdOptional(dto.marcaId())
                .orElseThrow(() -> new NotFoundException("Marca não encontrada"));

        modelo.setNomeModelo(dto.nomeModelo());
        modelo.setMarca(marca);

        return ModeloResponseDTO.valueOf(modelo);
    }

    @Transactional
    public void delete(Long id) {
        if (!modeloRepository.deleteById(id)) {
            throw new NotFoundException("Modelo não encontrado");
        }
    }

    public List<ModeloResponseDTO> findByMarcaId(Long marcaId) {
        return modeloRepository.findByMarcaId(marcaId).stream()
                .map(ModeloResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public List<ModeloResponseDTO> searchByNome(String nome) {
        return modeloRepository.findByNomeModelo(nome).stream()
                .map(ModeloResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}
