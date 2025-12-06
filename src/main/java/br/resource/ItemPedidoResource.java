package br.resource;

import java.util.List;

import br.dto.request.ItemPedidoRequestDTO;
import br.dto.response.ItemPedidoResponseDTO;
import br.service.ItemPedidoService;
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

@Path("/itens-pedido")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemPedidoResource {

    @Inject
    ItemPedidoService itemPedidoService;

    @Inject
    SecurityIdentity securityIdentity;

    @POST
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response criar(ItemPedidoRequestDTO dto) {
        ItemPedidoResponseDTO responseDTO = itemPedidoService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response atualizar(@PathParam("id") Long id, ItemPedidoRequestDTO dto) {
        ItemPedidoResponseDTO responseDTO = itemPedidoService.atualizar(id, dto);
        return Response.ok(responseDTO).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response listarTodos() {
        List<ItemPedidoResponseDTO> itens = itemPedidoService.listarTodos();
        return Response.ok(itens).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response buscarPorId(@PathParam("id") Long id) {
        ItemPedidoResponseDTO dto = itemPedidoService.buscarPorId(id);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response deletar(@PathParam("id") Long id) {
        itemPedidoService.deletar(id);
        return Response.noContent().build();
    }
}
