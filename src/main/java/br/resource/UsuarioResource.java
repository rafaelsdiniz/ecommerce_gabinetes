package br.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.dto.UsuarioResponseDTO;
import br.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @Path("/perfil")
    @RolesAllowed({"User", "Adm"})
    public Response buscarUsuarioLogado() {
        String username = jwt.getSubject();

        LOG.info("Requisição recebida para buscar perfil do usuário logado: " + username);

        UsuarioResponseDTO usuario = usuarioService.findByUsername(username);

        if (usuario == null) {
            LOG.warn("Usuário não encontrado para o username: " + username);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOG.info("Usuário encontrado: " + usuario.username());
        return Response.ok(usuario).build();
    }
}
