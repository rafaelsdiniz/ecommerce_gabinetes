package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.EnderecoRequestDTO;
import br.dto.EnderecoResponseDTO;
import br.entity.Endereco;
import br.repository.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EnderecoService {
    
    @Inject
    EnderecoRepository repository;

    @Transactional
    public void salvar(EnderecoRequestDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setCep(dto.getCep());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setEstado(dto.getEstado());
        endereco.setCliente(dto.getCliente());
        repository.persist(endereco);
    }

    public List<EnderecoResponseDTO> listarTodos(){
        return repository.listAll().stream()
            .map(endereco -> new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCep(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getEstado()))
            .collect(Collectors.toList());
    }

    public EnderecoResponseDTO buscarPorId(Long id) {
        Endereco endereco = repository.findById(id);
        if (endereco == null) {
            throw new NotFoundException("Endereço não encontrado, 404");
        }
        return new EnderecoResponseDTO(
            endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCep(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getEstado());
    }

    @Transactional
    public void atualizar(Long id, EnderecoRequestDTO dto) {
        Endereco endereco = repository.findById(id);
        if (endereco == null) {
            throw new NotFoundException("Endereço não encontrado, 404");
        }

        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setCep(dto.getCep());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setEstado(dto.getEstado());
        endereco.setCliente(dto.getCliente());
    }

    @Transactional 
    public void deletar(Long id){
        repository.deleteById(id);
    }
}


