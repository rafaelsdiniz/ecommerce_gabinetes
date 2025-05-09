package br.resource;

import java.util.List;

import br.dto.PagamentoRequestDTO;
import br.dto.PagamentoResponseDTO;
import br.service.PagamentoService;
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

    @Inject
    PagamentoService pagamentoService;

    @POST
    public Response salvar(@Valid PagamentoRequestDTO dto) {
        PagamentoResponseDTO responseDTO = pagamentoService.salvar(dto);
        return Response
            .status(Response.Status.CREATED)
            .entity(responseDTO)
            .build();
    }

    @GET
    public List<PagamentoResponseDTO> listarTodos() {
        return pagamentoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public PagamentoResponseDTO buscarPorId(@PathParam("id") Long id) {
        return pagamentoService.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid PagamentoRequestDTO dto) {
        pagamentoService.atualizar(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        pagamentoService.deletar(id);
        return Response.noContent().build();
    }
}
