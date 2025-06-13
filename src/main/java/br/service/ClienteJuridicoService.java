package br.service;


import java.util.List;
import java.util.stream.Collectors;

import br.dto.ClienteJuridicoRequestDTO;
import br.dto.ClienteJuridicoResponseDTO;
import br.model.ClienteJuridico;
import br.repository.ClienteJuridicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteJuridicoService {

    @Inject
    ClienteJuridicoRepository repository;

    @Transactional
    public ClienteJuridicoResponseDTO salvar(ClienteJuridicoRequestDTO dto) {
        ClienteJuridico cliente = new ClienteJuridico();
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setNomeFantasia(dto.getNomeFantasia());
        cliente.setRazaoSocial(dto.getRazaoSocial());
        cliente.setCnpj(dto.getCnpj());
        repository.persist(cliente);

        return new ClienteJuridicoResponseDTO(
            cliente.getId(),
            cliente.getEmail(),
            cliente.getTelefone(),
            cliente.getNomeFantasia(),
            cliente.getRazaoSocial(),
            cliente.getCnpj()
        );
    }
    
    public List<ClienteJuridicoResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(cliente -> new ClienteJuridicoResponseDTO(
                cliente.getId(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getNomeFantasia(),
                cliente.getRazaoSocial(),
                cliente.getCnpj()))
            .collect(Collectors.toList());
            
    }

    public ClienteJuridicoResponseDTO buscarPorId(Long id) {
                ClienteJuridico cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente Físico não encontrado");
        }
        return new ClienteJuridicoResponseDTO(
            cliente.getId(),
            cliente.getEmail(),
            cliente.getTelefone(),
            cliente.getNomeFantasia(),
            cliente.getRazaoSocial(),
            cliente.getCnpj()
        );
    }
    
    @Transactional
    public void atualizar(Long id, ClienteJuridicoRequestDTO dto) {
        ClienteJuridico cliente = repository.findById(id);
        if (cliente != null) {
            cliente.setEmail(dto.getEmail());
            cliente.setTelefone(dto.getTelefone());
            cliente.setNomeFantasia(dto.getNomeFantasia());
            cliente.setRazaoSocial(dto.getRazaoSocial());
            cliente.setCnpj(dto.getCnpj());
        }
    }
    
    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}