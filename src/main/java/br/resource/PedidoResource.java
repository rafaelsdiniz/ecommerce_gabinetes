package br.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.dto.PedidoRequestDTO;
import br.dto.PedidoResponseDTO;
import br.service.PedidoService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
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
import jakarta.ws.rs.core.Response.Status;

@Path("/pedidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PedidoResource {

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @Inject
    PedidoService pedidoService;

    @Inject
    SecurityIdentity securityIdentity;

    @POST
    @RolesAllowed("Adm")
    public Response criar(PedidoRequestDTO dto) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou criação de pedido", usuario);

        PedidoResponseDTO responseDTO = pedidoService.criar(dto);

        LOG.infof("Pedido criado com sucesso: ID=%d", responseDTO.getId());
        return Response.status(Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed({"Adm", "User"})
    public List<PedidoResponseDTO> listarTodos() {
        String usuario = securityIdentity.getPrincipal().getName();

        if (securityIdentity.hasRole("Adm")) {
            LOG.infof("Usuário '%s' (Adm) solicitou listagem de todos os pedidos", usuario);
            return pedidoService.listarTodos();
        } else {
            LOG.infof("Usuário '%s' (User) solicitou listagem dos seus próprios pedidos", usuario);
            return pedidoService.listarPorClienteLogado();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response buscarPorId(@PathParam("id") Long id) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou consulta de pedido por ID: %d", usuario, id);

        PedidoResponseDTO dto = pedidoService.buscarPorId(id);
        if (dto == null) {
            LOG.warnf("Pedido com ID %d não encontrado", id);
            return Response.status(Status.NOT_FOUND).build();
        }

        LOG.infof("Pedido encontrado: ID=%d,", dto.getCliente().getId());
        return Response.ok(dto).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response atualizar(@PathParam("id") Long id, PedidoRequestDTO dto) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou atualização do pedido ID=%d", usuario, id);

        pedidoService.atualizar(id, dto);

        LOG.infof("Pedido ID=%d atualizado com sucesso", id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response deletar(@PathParam("id") Long id) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou exclusão do pedido ID=%d", usuario, id);

        pedidoService.deletar(id);

        LOG.infof("Pedido ID=%d excluído com sucesso", id);
        return Response.noContent().build();
    }
}
