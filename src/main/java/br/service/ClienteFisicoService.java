package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.ClienteFisicoRequestDTO;
import br.dto.ClienteFisicoResponseDTO;
import br.dto.EnderecoRequestDTO;
import br.dto.EnderecoResponseDTO;
import br.entity.ClienteFisico;
import br.entity.Endereco;
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
    public void salvar(ClienteFisicoRequestDTO dto){
        ClienteFisico clienteFisico = new ClienteFisico();
        clienteFisico.setTelefone(dto.getTelefone());
        clienteFisico.setEmail(dto.getEmail());
        clienteFisico.setNome(dto.getNome());
        clienteFisico.setCpf(dto.getCpf());
        clienteFisico.setDataAniversario(dto.getDataAniversario());

        // Conversão do DTO de endereço para entidade
        Endereco endereco = new Endereco();
        EnderecoRequestDTO enderecoDTO = dto.getEndereco();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setEstado(enderecoDTO.getEstado());

        // Relaciona o endereço ao cliente
        endereco.setCliente(clienteFisico);
        clienteFisico.setEndereco(endereco);

        // Persiste o cliente (e o endereço junto, se tiver cascade)
        repository.persist(clienteFisico);
    }

    public List<ClienteFisicoResponseDTO> listarTodos() {
    return repository.listAll().stream().map(cliente -> new ClienteFisicoResponseDTO(
        cliente.getId(),
        cliente.getNome(),
        cliente.getCpf(),
        cliente.getDataAniversario(),
        cliente.getTelefone(),
        cliente.getEmail(),
        new EnderecoResponseDTO(
            cliente.getEndereco().getId(),
            cliente.getEndereco().getLogradouro(),
            cliente.getEndereco().getNumero(),
            cliente.getEndereco().getComplemento(),
            cliente.getEndereco().getCep(),
            cliente.getEndereco().getCidade(),
            cliente.getEndereco().getBairro(),
            cliente.getEndereco().getEstado()
        )
    )).collect(Collectors.toList());
}

public ClienteFisicoResponseDTO buscarPorId(Long id) {
    ClienteFisico cliente = repository.findById(id);
    if (cliente == null) {
        throw new NotFoundException("Cliente físico não encontrado");
    }

    return new ClienteFisicoResponseDTO(
        cliente.getId(),
        cliente.getNome(),
        cliente.getCpf(),
        cliente.getDataAniversario(),
        cliente.getTelefone(),
        cliente.getEmail(),
        new EnderecoResponseDTO(
            cliente.getEndereco().getId(),
            cliente.getEndereco().getLogradouro(),
            cliente.getEndereco().getNumero(),
            cliente.getEndereco().getComplemento(),
            cliente.getEndereco().getCep(),
            cliente.getEndereco().getCidade(),
            cliente.getEndereco().getBairro(),
            cliente.getEndereco().getEstado()
        )
    );
}

@Transactional
public void deletar(Long id){
    if (!repository.deleteById(id)) {
        throw new NotFoundException("Cliente físico não encontrado");
    }
}
}

