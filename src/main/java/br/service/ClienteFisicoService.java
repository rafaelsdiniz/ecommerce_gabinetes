package br.service;


import java.util.List;
import java.util.stream.Collectors;

import br.dto.ClienteFisicoRequestDTO;
import br.dto.ClienteFisicoResponseDTO;
import br.model.ClienteFisico;
import br.repository.ClienteFisicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteFisicoService {

    @Inject
    ClienteFisicoRepository repository;

    @Transactional
    public ClienteFisicoResponseDTO salvar(ClienteFisicoRequestDTO dto) {
        ClienteFisico cliente = new ClienteFisico();
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());
        cliente.setIdade(dto.getIdade());
        cliente.setNome(dto.getNome());
        cliente.setSobreNome(dto.getSobreNome());
        repository.persist(cliente);

        return new ClienteFisicoResponseDTO(
            cliente.getId(),
            cliente.getEmail(),
            cliente.getTelefone(),
            cliente.getNome(),
            cliente.getSobreNome(),
            cliente.getCpf(),
            cliente.getIdade()
        );
    }
    
    public List<ClienteFisicoResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(cliente -> new ClienteFisicoResponseDTO(
                cliente.getId(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getNome(),
                cliente.getSobreNome(),
                cliente.getCpf(),
                cliente.getIdade()))
            .collect(Collectors.toList());
            
    }

    public ClienteFisicoResponseDTO buscarPorId(Long id) {
                ClienteFisico cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente Físico não encontrado");
        }
        return new ClienteFisicoResponseDTO(
            cliente.getId(),
            cliente.getEmail(),
            cliente.getTelefone(),
            cliente.getNome(),
            cliente.getSobreNome(),
            cliente.getCpf(),
            cliente.getIdade()
        );
    }
    
    @Transactional
    public void atualizar(Long id, ClienteFisicoRequestDTO dto) {
        ClienteFisico cliente = repository.findById(id);
        if (cliente != null) {
            cliente.setEmail(dto.getEmail());
            cliente.setTelefone(dto.getTelefone());
            cliente.setCpf(dto.getCpf());
            cliente.setIdade(dto.getIdade());
            cliente.setNome(dto.getNome());
            cliente.setSobreNome(dto.getSobreNome());
        }
    }
    
    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
