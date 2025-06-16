package br.dto;

import br.model.Cliente;
import br.model.ClienteFisico;
import br.model.ClienteJuridico;
import br.model.Perfil;
import br.model.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String username,
    Perfil perfil) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;

        String nome = null;
        Cliente cliente = usuario.getCliente();

        if (cliente instanceof ClienteFisico fisico) {
            nome = fisico.getNome();
        } else if (cliente instanceof ClienteJuridico juridico) {
            nome = juridico.getNomeFantasia(); 
        }

        return new UsuarioResponseDTO(
            usuario.getId(),
            nome,
            usuario.getUsername(),
            usuario.getPerfil()
        );
    }

    public Long id() {
        return id;
    }

    public String nome() {
        return nome;
    }

    public String username() {
        return username;
    }

    public Perfil perfil() {
        return perfil;
    }

}
 