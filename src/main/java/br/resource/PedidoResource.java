package br.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.dto.request.PedidoRequestDTO;
import br.dto.response.ClienteResponseDTO;
import br.dto.response.PedidoResponseDTO;
import br.service.ClienteService;
import br.model.Cliente;
import br.model.enums.StatusPedido;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/pedidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PedidoResource {

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @Inject
    PedidoService pedidoService;
    
    @Inject
    ClienteService clienteService;

    @Inject
    SecurityIdentity securityIdentity;

    // ------------------- POST -------------------
    @POST
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response criar(PedidoRequestDTO dto) {
        String clienteEmail = securityIdentity.getPrincipal().getName();
        LOG.infof("Cliente '%s' solicitou criação de pedido", clienteEmail);

        PedidoResponseDTO responseDTO = pedidoService.criar(dto);
        LOG.infof("Pedido criado com sucesso: ID=%d", responseDTO.id());

        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @POST
    @Path("/{id}/finalizar")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response finalizarPedido(@PathParam("id") Long id) {
        String clienteEmail = securityIdentity.getPrincipal().getName();
        LOG.infof("Cliente '%s' solicitou finalização do pedido ID=%d", clienteEmail, id);

        PedidoResponseDTO responseDTO = pedidoService.finalizarPedido(id);
        return Response.ok(responseDTO).build();
    }

    @POST
    @Path("/{id}/cancelar")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response cancelarPedido(@PathParam("id") Long id) {
        String clienteEmail = securityIdentity.getPrincipal().getName();
        LOG.infof("Cliente '%s' solicitou cancelamento do pedido ID=%d", clienteEmail, id);

        PedidoResponseDTO responseDTO = pedidoService.cancelarPedido(id);
        return Response.ok(responseDTO).build();
    }

    // ------------------- PUT -------------------
    @PUT
    @Path("/{id}/status")
    @RolesAllowed("ADMIN")
    public Response atualizarStatus(@PathParam("id") Long id, @QueryParam("status") StatusPedido status) {
        String clienteEmail = securityIdentity.getPrincipal().getName();
        LOG.infof("Admin '%s' atualizando status do pedido ID=%d para %s", clienteEmail, id, status);

        PedidoResponseDTO responseDTO = pedidoService.atualizarStatus(id, status);
        return Response.ok(responseDTO).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response atualizar(@PathParam("id") Long id, PedidoRequestDTO dto) {
        String clienteEmail = securityIdentity.getPrincipal().getName();
        LOG.infof("Cliente '%s' solicitou atualização do pedido ID=%d", clienteEmail, id);

        pedidoService.atualizar(id, dto);
        return Response.noContent().build();
    }

    // ------------------- GET -------------------
    @GET
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response listarTodos() {
        String clienteEmail = securityIdentity.getPrincipal().getName();

        if (securityIdentity.hasRole("ADMIN")) {
            LOG.infof("Admin '%s' solicitou listagem de todos os pedidos", clienteEmail);
            return Response.ok(pedidoService.listarTodos()).build();
        } else {
            LOG.infof("Cliente '%s' solicitou listagem dos seus próprios pedidos", clienteEmail);
            return Response.ok(pedidoService.listarPorClienteLogado()).build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response buscarPorId(@PathParam("id") Long id) {
        PedidoResponseDTO dto = pedidoService.buscarPorId(id);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/status/{status}")
    @RolesAllowed("ADMIN")
    public Response buscarPorStatus(@PathParam("status") StatusPedido status) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorStatus(status);
        return Response.ok(pedidos).build();
    }

    @GET
    @Path("/cliente/{clienteId}/historico")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response buscarHistoricoCliente(@PathParam("clienteId") Long clienteId) {
        List<PedidoResponseDTO> historico = pedidoService.buscarHistoricoCliente(clienteId);
        return Response.ok(historico).build();
    }
    
    @GET
    @Path("/meus-pedidos")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response listarMeusPedidos(@Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();

        if (email == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Usuário não autenticado")
                    .build();
        }

        // Usar o método correto: findByEmail que retorna ClienteResponseDTO
        ClienteResponseDTO clienteDTO = clienteService.findByEmail(email);
        if (clienteDTO == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado")
                    .build();
        }

        // Usar o método buscarHistoricoCliente que já existe no PedidoService
        List<PedidoResponseDTO> pedidos = pedidoService.buscarHistoricoCliente(clienteDTO.id());
        return Response.ok(pedidos).build();
    }

    // ------------------- DELETE -------------------
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response deletar(@PathParam("id") Long id) {
        pedidoService.deletar(id);
        return Response.noContent().build();
    }
}