package br.resource;

import java.util.List;

import br.dto.ClienteJuridicoRequestDTO;
import br.dto.ClienteJuridicoResponseDTO;
import br.service.ClienteJuridicoService;
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

@Path("/clientes/juridicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteJuridicoResource {

    @Inject
    ClienteJuridicoService service;

    @POST
    public Response salvar(@Valid ClienteJuridicoRequestDTO dto) {
        ClienteJuridicoResponseDTO responseDTO = service.salvar(dto);
        return Response
                .status(Response.Status.CREATED)
                .entity(responseDTO)
                .build();
    }

    @GET
    public List<ClienteJuridicoResponseDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public ClienteJuridicoResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, @Valid ClienteJuridicoRequestDTO dto) {
        service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
