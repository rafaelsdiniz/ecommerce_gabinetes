package br.resource;

import java.util.List;

import br.dto.PedidoRequestDTO;
import br.dto.PedidoResponseDTO;
import br.service.PedidoService;
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

    @Inject
    PedidoService pedidoService;

    @POST
    public Response criar(PedidoRequestDTO dto) {
        PedidoResponseDTO responseDTO = pedidoService.criar(dto);
        return Response.status(Status.CREATED).entity(responseDTO).build();
    }

    @GET
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        PedidoResponseDTO dto = pedidoService.buscarPorId(id);
        if (dto == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(dto).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, PedidoRequestDTO dto) {
        pedidoService.atualizar(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        pedidoService.deletar(id);
        return Response.noContent().build();
    }
}
