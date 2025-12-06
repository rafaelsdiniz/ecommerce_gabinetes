package br.resource;

import org.jboss.logging.Logger;

import br.dto.AuthDTO;
import br.dto.response.ClienteResponseDTO;
import br.service.ClienteService;
import br.service.HashService;
import br.service.JwtService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    ClienteService clienteService;

    @POST
    public Response login(AuthDTO dto) {
        LOG.info("Tentativa de login para o e-mail: " + dto.email());

        try {
            // Busca cliente pelo e-mail
            ClienteResponseDTO cliente = clienteService.findByEmail(dto.email());
            if (cliente == null) {
                LOG.warn("Falha de autenticação: e-mail não encontrado - " + dto.email());
                return Response.status(Status.UNAUTHORIZED).entity("E-mail ou senha inválidos").build();
            }

            // Gera hash da senha recebida
            String hash = hashService.getHashSenha(dto.senha());

            // Verifica se a senha bate com a armazenada
            boolean senhaValida = clienteService.validarSenha(dto.email(), hash);
            if (!senhaValida) {
                LOG.warn("Falha de autenticação: senha incorreta para o e-mail - " + dto.email());
                return Response.status(Status.UNAUTHORIZED).entity("E-mail ou senha inválidos").build();
            }

            // Gera token JWT
            String token = jwtService.generateJwt(cliente.email(), cliente.perfil().name());
            LOG.info("Login bem-sucedido para o e-mail: " + dto.email());

            // Retorna token no cabeçalho
            return Response.ok()
                    .header("Authorization", "Bearer " + token)
                    .entity(cliente)
                    .build();

        } catch (Exception e) {
            LOG.error("Erro ao autenticar cliente: " + dto.email(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro interno no login").build();
        }
    }

        @GET
    @Path("/dev-token")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String gerarTokenDev() {
        // Cria um token com perfil Adm ou User
        return jwtService.generateJwt("dev@dev.com", "Adm");
    }

}
