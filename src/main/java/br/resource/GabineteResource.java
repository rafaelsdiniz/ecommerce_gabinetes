package br.resource;

import java.util.List;

import br.dto.GabineteRequestDTO;
import br.dto.GabineteResponseDTO;
import br.service.GabineteService;
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

@Path("/gabinetes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GabineteResource {

    @Inject
    GabineteService service;

    @POST
    public Response salvar(@Valid GabineteRequestDTO dto) {
    GabineteResponseDTO responseDTO = service.salvar(dto); 
    return Response
        .status(Response.Status.CREATED)
        .entity(responseDTO)
        .build();
    }   


    @GET
    public List<GabineteResponseDTO> listar() {
        return service.listarTodos();
    }
    
    @GET
    @Path("/{id}")
    public GabineteResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, @Valid GabineteRequestDTO dto) {
        service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
