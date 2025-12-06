package br.service;

import java.util.List;
import java.util.stream.Collectors;

import br.dto.request.FornecedorRequestDTO;
import br.dto.response.FornecedorResponseDTO;
import br.model.Fornecedor;
import br.repository.FornecedorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FornecedorService {

    @Inject
    FornecedorRepository fornecedorRepository;

    public List<FornecedorResponseDTO> findAll() {
        return fornecedorRepository.listAll().stream()
                .map(FornecedorResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public FornecedorResponseDTO findById(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado"));
        return FornecedorResponseDTO.valueOf(fornecedor);
    }

    @Transactional
    public FornecedorResponseDTO create(FornecedorRequestDTO dto) {
        // Validar CNPJ duplicado
        if (fornecedorRepository.findByCnpj(dto.cnpj()).isPresent()) {
            throw new IllegalArgumentException("CNPJ já cadastrado");
        }
        
        // Validar email duplicado
        if (fornecedorRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Fornecedor fornecedor = new Fornecedor(
            dto.nome(),
            dto.email(),
            dto.telefone(),
            dto.cnpj()
        );

        fornecedorRepository.persist(fornecedor);
        return FornecedorResponseDTO.valueOf(fornecedor);
    }

    @Transactional
    public FornecedorResponseDTO update(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado"));

        // Validar CNPJ duplicado (exceto o próprio fornecedor)
        fornecedorRepository.findByCnpj(dto.cnpj()).ifPresent(f -> {
            if (!f.getId().equals(id)) {
                throw new IllegalArgumentException("CNPJ já cadastrado");
            }
        });
        
        // Validar email duplicado (exceto o próprio fornecedor)
        fornecedorRepository.findByEmail(dto.email()).ifPresent(f -> {
            if (!f.getId().equals(id)) {
                throw new IllegalArgumentException("Email já cadastrado");
            }
        });

        fornecedor.setNome(dto.nome());
        fornecedor.setEmail(dto.email());
        fornecedor.setTelefone(dto.telefone());
        fornecedor.setCnpj(dto.cnpj());

        return FornecedorResponseDTO.valueOf(fornecedor);
    }

    @Transactional
    public void delete(Long id) {
        if (!fornecedorRepository.deleteById(id)) {
            throw new NotFoundException("Fornecedor não encontrado");
        }
    }

    public FornecedorResponseDTO findByCnpj(String cnpj) {
        Fornecedor fornecedor = fornecedorRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado"));
        return FornecedorResponseDTO.valueOf(fornecedor);
    }
}
