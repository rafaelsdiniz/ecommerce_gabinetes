package br.dto.response;

import br.model.enums.Perfil;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String email,
    String telefone,
    String cpf,
    Perfil perfil
) {}