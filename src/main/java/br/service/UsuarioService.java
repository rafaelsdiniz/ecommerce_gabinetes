package br.service;

import br.dto.UsuarioResponseDTO;

public interface UsuarioService {

    UsuarioResponseDTO findByUsernameAndSenha(String username, String senha);
    UsuarioResponseDTO findByUsername(String username);

    
}