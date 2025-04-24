package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.ClienteJuridicoRequestDTO;
import br.dto.ClienteJuridicoResponseDTO;
import br.dto.EnderecoRequestDTO;
import br.dto.EnderecoResponseDTO;
import br.entity.ClienteJuridico;
import br.entity.Endereco;
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
    public void salvar(ClienteJuridicoRequestDTO dto) {
        ClienteJuridico cliente = new ClienteJuridico();
        cliente.setRazaoSocial(dto.getRazaoSocial());
        cliente.setNomeFantasia(dto.getNomeFantasia());
        cliente.setCnpj(dto.getCnpj());
        cliente.setInscricaoEstadual(dto.getInscricaoEstadual());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());

        Endereco endereco = new Endereco();
        EnderecoRequestDTO enderecoDTO = dto.getEndereco();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setCliente(cliente);

        cliente.setEndereco(endereco);
        repository.persist(cliente);
    }

    public List<ClienteJuridicoResponseDTO> listarTodos() {
        return repository.listAll().stream().map(cliente -> new ClienteJuridicoResponseDTO(
            cliente.getId(),
            cliente.getRazaoSocial(),
            cliente.getNomeFantasia(),
            cliente.getCnpj(),
            cliente.getInscricaoEstadual(),
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

    public ClienteJuridicoResponseDTO buscarPorId(Long id) {
        ClienteJuridico cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente jurídico não encontrado");
        }

        return new ClienteJuridicoResponseDTO(
            cliente.getId(),
            cliente.getRazaoSocial(),
            cliente.getNomeFantasia(),
            cliente.getCnpj(),
            cliente.getInscricaoEstadual(),
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
    public void deletar(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Cliente jurídico não encontrado");
        }
    }
}
