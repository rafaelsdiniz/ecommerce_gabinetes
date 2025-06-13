package br.service;


import java.util.List;
import java.util.stream.Collectors;

import br.dto.GabineteRequestDTO;
import br.dto.GabineteResponseDTO;
import br.model.Gabinete;
import br.repository.GabineteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GabineteService {

    @Inject
    GabineteRepository repository;

    @Transactional
    public GabineteResponseDTO salvar(GabineteRequestDTO dto) {
        Gabinete Gabinete = new Gabinete();
        Gabinete.setNomeExibicao(dto.getNomeExibicao());
        Gabinete.setMarca(dto.getMarca());
        Gabinete.setPreco(dto.getPreco());
        Gabinete.setCor(dto.getCor());
        Gabinete.setFormato(dto.getFormato());
        Gabinete.setAltura(dto.getAltura());
        Gabinete.setLargura(dto.getLargura());
        Gabinete.setPeso(dto.getPeso());
        Gabinete.setTamanhoMaxGpu(dto.getTamanhoMaxGpu());
        Gabinete.setAlturaMaxCooler(dto.getAlturaMaxCooler());
        Gabinete.setQtdRgb(dto.getQtdRgb());
        Gabinete.setUsb(dto.getUsb());
        Gabinete.setUsbc(dto.getUsbc());
        repository.persist(Gabinete);

        return new GabineteResponseDTO(
            Gabinete.getId(),
            Gabinete.getNomeExibicao(),
            Gabinete.getMarca(),
            Gabinete.getPreco(),
            Gabinete.getCor(),
            Gabinete.getFormato(),
            Gabinete.getAltura(),
            Gabinete.getLargura(),
            Gabinete.getPeso(),
            Gabinete.getTamanhoMaxGpu(),
            Gabinete.getAlturaMaxCooler(),
            Gabinete.getQtdRgb(),
            Gabinete.getUsb(),
            Gabinete.getUsbc()
        );
    }
    
    public List<GabineteResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(Gabinete -> new GabineteResponseDTO(
                Gabinete.getId(),
                Gabinete.getNomeExibicao(),
                Gabinete.getMarca(),
                Gabinete.getPreco(),
                Gabinete.getCor(),
                Gabinete.getFormato(),
                Gabinete.getAltura(),
                Gabinete.getLargura(),
                Gabinete.getPeso(),
                Gabinete.getTamanhoMaxGpu(),
                Gabinete.getAlturaMaxCooler(),
                Gabinete.getQtdRgb(),
                Gabinete.getUsb(),
                Gabinete.getUsbc()))
            .collect(Collectors.toList());
            
    }

    public GabineteResponseDTO buscarPorId(Long id) {
                Gabinete Gabinete = repository.findById(id);
        if (Gabinete == null) {
            throw new NotFoundException("Gabinete Físico não encontrado");
        }
        return new GabineteResponseDTO(
            Gabinete.getId(),
            Gabinete.getNomeExibicao(),
            Gabinete.getMarca(),
            Gabinete.getPreco(),
            Gabinete.getCor(),
            Gabinete.getFormato(),
            Gabinete.getAltura(),
            Gabinete.getLargura(),
            Gabinete.getPeso(),
            Gabinete.getTamanhoMaxGpu(),
            Gabinete.getAlturaMaxCooler(),
            Gabinete.getQtdRgb(),
            Gabinete.getUsb(),
            Gabinete.getUsbc()
        );
    }
    
    @Transactional
    public void atualizar(Long id, GabineteRequestDTO dto) {
        Gabinete Gabinete = repository.findById(id);
        if (Gabinete != null) {
            Gabinete.setNomeExibicao(dto.getNomeExibicao());
            Gabinete.setMarca(dto.getMarca());
            Gabinete.setPreco(dto.getPreco());
            Gabinete.setCor(dto.getCor());
            Gabinete.setFormato(dto.getFormato());
            Gabinete.setAltura(dto.getAltura());
            Gabinete.setLargura(dto.getLargura());
            Gabinete.setPeso(dto.getPeso());
            Gabinete.setTamanhoMaxGpu(dto.getTamanhoMaxGpu());
            Gabinete.setAlturaMaxCooler(dto.getAlturaMaxCooler());
            Gabinete.setQtdRgb(dto.getQtdRgb());
            Gabinete.setUsb(dto.getUsb());
            Gabinete.setUsbc(dto.getUsbc());
        }
    }
    
    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}