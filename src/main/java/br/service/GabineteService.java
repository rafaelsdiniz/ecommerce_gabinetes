package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.GabineteRequestDTO;
import br.dto.GabineteResponseDTO;
import br.entity.Gabinete;
import br.repository.GabineteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped // pro quarkus gerenciar
public class GabineteService {
    
    @Inject
    GabineteRepository repository;

    @Transactional
    public void salvar(GabineteRequestDTO dto){
        Gabinete gabinete = new Gabinete();
        gabinete.setNomeExibicao(dto.getNomeExibicao());
        gabinete.setMarca(dto.getMarca());
        gabinete.setPreco(dto.getPreco());
        gabinete.setCor(dto.getCor());
        gabinete.setFormato(dto.getFormato());
        gabinete.setAltura(dto.getAltura());
        gabinete.setPeso(dto.getPeso());
        gabinete.setSuportaWaterCooler(dto.isSuportaWaterCooler());
        gabinete.setTamanhoMaxGpu(dto.getTamanhoMaxGpu());
        gabinete.setAlturaMaxCooler(dto.getAlturaMaxCooler());
        gabinete.setRbg(dto.isRgb());
        gabinete.setUsb(dto.getUsb());
        gabinete.setUsbc(dto.getUsbc());
        repository.persist(gabinete);

    }

    public List<GabineteResponseDTO> listarTodos() {
        return repository.listAll().stream()
              .map(gabinete -> new GabineteResponseDTO(
                    gabinete.getId(),
                    gabinete.getNomeExibicao(),
                    gabinete.getMarca(),
                    gabinete.getPreco(),
                    gabinete.getCor(),
                    gabinete.getFormato(),
                    gabinete.getAltura(),
                    gabinete.getLargura(),
                    gabinete.getPeso(),
                    gabinete.isSuportaWaterCooler(),
                    gabinete.getTamanhoMaxGpu(),
                    gabinete.getAlturaMaxCooler(),
                    gabinete.isRbg(),
                    gabinete.getUsb(),
                    gabinete.getUsbc()))
                .collect(Collectors.toList());
            
    }

    public GabineteResponseDTO buscarPorID(long id) {
        Gabinete gabinete = repository.findById(id);
        if (gabinete == null) {
            throw new NotFoundException("Marca não encontrada, 404");
        }
        return new GabineteResponseDTO(
                    gabinete.getId(),
                    gabinete.getNomeExibicao(),
                    gabinete.getMarca(),
                    gabinete.getPreco(),
                    gabinete.getCor(),
                    gabinete.getFormato(),
                    gabinete.getAltura(),
                    gabinete.getLargura(),
                    gabinete.getPeso(),
                    gabinete.isSuportaWaterCooler(),
                    gabinete.getTamanhoMaxGpu(),
                    gabinete.getAlturaMaxCooler(),
                    gabinete.isRbg(),
                    gabinete.getUsb(),
                    gabinete.getUsbc());
    }

    @Transactional
    public void atualizar(long id, GabineteRequestDTO dto) {
        Gabinete gabinete = repository.findById(id);
        if(gabinete != null){
            gabinete.setNomeExibicao(dto.getNomeExibicao());
            gabinete.setMarca(dto.getMarca());
            gabinete.setPreco(dto.getPreco());
            gabinete.setCor(dto.getCor());
            gabinete.setFormato(dto.getFormato());
            gabinete.setAltura(dto.getAltura());
            gabinete.setPeso(dto.getPeso());
            gabinete.setSuportaWaterCooler(dto.isSuportaWaterCooler());
            gabinete.setTamanhoMaxGpu(dto.getTamanhoMaxGpu());
            gabinete.setAlturaMaxCooler(dto.getAlturaMaxCooler());
            gabinete.setRbg(dto.isRgb());
            gabinete.setUsb(dto.getUsb());
            gabinete.setUsbc(dto.getUsbc());
        }
    }

    @Transactional
    public void deletar(long id){
        repository.deleteById(id);
    }
}
