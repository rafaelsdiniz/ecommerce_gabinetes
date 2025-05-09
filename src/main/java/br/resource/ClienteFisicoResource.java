package br.resource;

import java.util.List;

import br.dto.ClienteFisicoRequestDTO;
import br.dto.ClienteFisicoResponseDTO;
import br.service.ClienteFisicoService;
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

@Path("/clientes/fisicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteFisicoResource {

    @Inject
    ClienteFisicoService service;

    @POST
    public Response salvar(@Valid ClienteFisicoRequestDTO dto) {
        ClienteFisicoResponseDTO responseDTO = service.salvar(dto);
        return Response
                .status(Response.Status.CREATED)
                .entity(responseDTO)
                .build();
    }

    @GET
    public List<ClienteFisicoResponseDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public ClienteFisicoResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, @Valid ClienteFisicoRequestDTO dto) {
        service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
