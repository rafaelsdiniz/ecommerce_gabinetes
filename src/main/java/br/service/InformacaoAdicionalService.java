package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.request.InformacaoAdicionalRequestDTO;
import br.dto.response.InformacaoAdicionalResponseDTO;
import br.model.Gabinete;
import br.model.InformacaoAdicional;
import br.repository.GabineteRepository;
import br.repository.InformacaoAdicionalRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class InformacaoAdicionalService {

    @Inject
    InformacaoAdicionalRepository infoRepository;
    
    @Inject
    GabineteRepository gabineteRepository;

    public List<InformacaoAdicionalResponseDTO> findAll() {
        return infoRepository.listAll().stream()
                .map(InformacaoAdicionalResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public InformacaoAdicionalResponseDTO findById(Long id) {
        InformacaoAdicional info = infoRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Informação adicional não encontrada"));
        return InformacaoAdicionalResponseDTO.valueOf(info);
    }

    @Transactional
    public InformacaoAdicionalResponseDTO create(InformacaoAdicionalRequestDTO dto) {
        Gabinete gabinete = gabineteRepository.findByIdOptional(dto.gabineteId())
                .orElseThrow(() -> new NotFoundException("Gabinete não encontrado"));

        InformacaoAdicional info = new InformacaoAdicional(
            dto.titulo(),
            dto.descricao(),
            gabinete
        );
        
        infoRepository.persist(info);
        return InformacaoAdicionalResponseDTO.valueOf(info);
    }

    @Transactional
    public InformacaoAdicionalResponseDTO update(Long id, InformacaoAdicionalRequestDTO dto) {
        InformacaoAdicional info = infoRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Informação adicional não encontrada"));

        Gabinete gabinete = gabineteRepository.findByIdOptional(dto.gabineteId())
                .orElseThrow(() -> new NotFoundException("Gabinete não encontrado"));

        info.setTitulo(dto.titulo());
        info.setDescricao(dto.descricao());
        info.setGabinete(gabinete);

        return InformacaoAdicionalResponseDTO.valueOf(info);
    }

    @Transactional
    public void delete(Long id) {
        if (!infoRepository.deleteById(id)) {
            throw new NotFoundException("Informação adicional não encontrada");
        }
    }

    public List<InformacaoAdicionalResponseDTO> findByGabineteId(Long gabineteId) {
        return infoRepository.findByGabineteId(gabineteId).stream()
                .map(InformacaoAdicionalResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}
