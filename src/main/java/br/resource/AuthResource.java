package br.resource;

import org.jboss.logging.Logger;

import br.dto.AuthDTO;
import br.dto.UsuarioResponseDTO;
import br.service.HashService;
import br.service.JwtService;
import br.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AuthDTO dto) {
        LOG.info("Tentativa de login para o usuário: " + dto.username());

        String hash;
        try {
            hash = hashService.getHashSenha(dto.senha());
        } catch (Exception e) {
            LOG.error("Erro ao gerar hash da senha para o usuário: " + dto.username(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        UsuarioResponseDTO usuario = usuarioService.findByUsernameAndSenha(dto.username(), hash);

        if (usuario == null) {
            LOG.warn("Falha de autenticação para o usuário: " + dto.username());
            return Response.status(Status.UNAUTHORIZED).build();
        }

        String token = jwtService.generateJwt(usuario.username(), usuario.perfil().getNome());
        LOG.info("Login bem-sucedido para o usuário: " + dto.username() + ". Token gerado.");

        return Response.ok().header("Authorization", token).build();
    }
}
