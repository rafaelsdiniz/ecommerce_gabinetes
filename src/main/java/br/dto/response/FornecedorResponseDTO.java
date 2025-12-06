package br.dto.response;

import br.model.Fornecedor;

public record FornecedorResponseDTO (
    Long id,
    String nome,
    String email,
    String telefone,
    String cnpj
) {
    public static FornecedorResponseDTO valueOf(Fornecedor fornecedor) {
        return new FornecedorResponseDTO(
            fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getEmail(),
            fornecedor.getTelefone(),
            fornecedor.getCnpj()
        );
    }
}
