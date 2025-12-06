package br.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.dto.request.PagamentoRequestDTO;
import br.dto.response.PagamentoResponseDTO;
import br.service.PagamentoService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    private static final Logger LOG = Logger.getLogger(PagamentoResource.class);

    @Inject
    PagamentoService pagamentoService;

    @Inject
    SecurityIdentity securityIdentity;

    @POST
    @RolesAllowed("ADMIN")
    public Response salvar(@Valid PagamentoRequestDTO dto) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou criação de pagamento: pedidoId=%d, valor=%.2f",
                usuario, dto.pedidoId(), dto.valor());

        PagamentoResponseDTO responseDTO = pagamentoService.salvar(dto);

        LOG.infof("Pagamento criado com sucesso: ID=%d, pedidoId=%d",
                responseDTO.id(), responseDTO.pedidoId());

        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed("ADMIN")
    public List<PagamentoResponseDTO> listarTodos() {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou listagem de todos os pagamentos", usuario);
        return pagamentoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public PagamentoResponseDTO buscarPorId(@PathParam("id") Long id) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou pagamento por ID: %d", usuario, id);

        return pagamentoService.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response atualizar(@PathParam("id") Long id, @Valid PagamentoRequestDTO dto) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou atualização do pagamento ID=%d", usuario, id);

        pagamentoService.atualizar(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response deletar(@PathParam("id") Long id) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou exclusão do pagamento ID=%d", usuario, id);

        pagamentoService.deletar(id);
        return Response.noContent().build();
    }
}
