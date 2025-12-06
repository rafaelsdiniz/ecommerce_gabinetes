package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.request.AtualizarClienteDTO;
import br.dto.request.ClienteRequestDTO;
import br.dto.request.TrocarSenhaDTO;
import br.dto.request.EnderecoRequestDTO;
import br.dto.response.ClienteResponseDTO;
import br.model.Cliente;
import br.model.Endereco;
import br.model.enums.Perfil;
import br.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    HashService hashService;

    // ============================================================
    // CRIAR CLIENTE
    // ============================================================
    @Transactional
    public ClienteResponseDTO salvar(ClienteRequestDTO dto) {
        if (clienteRepository.findByCpf(dto.cpf()).isPresent()) {
            throw new WebApplicationException("CPF já cadastrado", 400);
        }

        if (clienteRepository.findByEmail(dto.email()).isPresent()) {
            throw new WebApplicationException("Email já cadastrado", 400);
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setCpf(dto.cpf());

        try {
            if (dto.senha() != null && !dto.senha().isBlank()) {
                cliente.setSenha(hashService.getHashSenha(dto.senha()));
            } else {
                throw new WebApplicationException("Senha é obrigatória", 400);
            }
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao gerar hash da senha", 500);
        }

        cliente.setPerfil(Perfil.CLIENTE);

        clienteRepository.persist(cliente);
        return toResponseDTO(cliente);
    }

    // ============================================================
    // LISTAR TODOS
    // ============================================================
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.listAll()
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    // ============================================================
    // BUSCAR POR ID
    // ============================================================
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }
        return toResponseDTO(cliente);
    }

    // ============================================================
    // BUSCAR POR CPF
    // ============================================================
    public ClienteResponseDTO buscarPorCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
            .orElseThrow(() -> new NotFoundException("Cliente não encontrado com CPF: " + cpf));
        return toResponseDTO(cliente);
    }

    // ============================================================
    // BUSCAR POR EMAIL
    // ============================================================
    public ClienteResponseDTO findByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
            .orElse(null);
        if (cliente == null)
            return null;
        return toResponseDTO(cliente);
    }

    // ============================================================
    // VALIDAR SENHA
    // ============================================================
    public boolean validarSenha(String email, String hashSenha) {
        return clienteRepository.findByEmail(email)
            .map(c -> c.getSenha().equals(hashSenha))
            .orElse(false);
    }

    // ============================================================
    // ATUALIZAR CLIENTE
    // ============================================================
    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }

        clienteRepository.findByCpf(dto.cpf()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new WebApplicationException("CPF já cadastrado para outro cliente", 400);
            }
        });

        clienteRepository.findByEmail(dto.email()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new WebApplicationException("Email já cadastrado para outro cliente", 400);
            }
        });

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setCpf(dto.cpf());

        try {
            if (dto.senha() != null && !dto.senha().isBlank()) {
                cliente.setSenha(hashService.getHashSenha(dto.senha()));
            }
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao gerar hash da senha", 500);
        }

        return toResponseDTO(cliente);
    }

    // ============================================================
    // ATUALIZAR PERFIL (novo método)
    // ============================================================
    @Transactional
    public ClienteResponseDTO atualizarPerfil(Long id, AtualizarClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }

        // Verifica se email já existe para outro cliente
        clienteRepository.findByEmail(dto.email()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new WebApplicationException("Email já cadastrado para outro cliente", 400);
            }
        });

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        // CPF não é alterado nesta operação

        return toResponseDTO(cliente);
    }

    // ============================================================
    // ALTERAR SENHA
    // ============================================================
    @Transactional
    public void alterarSenha(Long id, TrocarSenhaDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }

        try {
            if (!hashService.validarSenha(dto.senhaAtual(), cliente.getSenha())) {
                throw new WebApplicationException("Senha atual incorreta", 400);
            }
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao validar senha atual", 500);
        }

        if (dto.novaSenha() == null || dto.novaSenha().isBlank()) {
            throw new WebApplicationException("Nova senha é obrigatória", 400);
        }

        if (dto.novaSenha().length() < 6) {
            throw new WebApplicationException("Nova senha deve ter pelo menos 6 caracteres", 400);
        }

        try {
            cliente.setSenha(hashService.getHashSenha(dto.novaSenha()));
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao gerar hash da senha", 500);
        }
    }

    // ============================================================
    // DELETAR CLIENTE
    // ============================================================
    @Transactional
    public void deletar(Long id) {
        if (!clienteRepository.deleteById(id)) {
            throw new NotFoundException("Cliente não encontrado");
        }
    }

    // ============================================================
    // CONVERSÕES
    // ============================================================
    private Endereco toEnderecoEntity(EnderecoRequestDTO dto) {
        if (dto == null) return null;

        Endereco endereco = new Endereco();
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        return endereco;
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getTelefone(),
            cliente.getCpf(),
            cliente.getPerfil()
        );
    }
}