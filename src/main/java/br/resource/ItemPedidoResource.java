package br.resource;

import java.util.List;

import br.dto.ItemPedidoRequestDTO;
import br.dto.ItemPedidoResponseDTO;
import br.service.ItemPedidoService;
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

@Path("/itempedido")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemPedidoResource {

    @Inject
    ItemPedidoService service;

    @POST
    public Response salvar(@Valid ItemPedidoRequestDTO dto) {
        ItemPedidoResponseDTO responseDTO = service.criar(dto);
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    public List<ItemPedidoResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public ItemPedidoResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid ItemPedidoRequestDTO dto) {
        service.atualizar(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        service.deletar(id);
        return Response.noContent().build();
    }
}
