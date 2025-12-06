package br.resource;

import java.util.List;

import br.dto.request.ModeloRequestDTO;
import br.dto.response.ModeloResponseDTO;
import br.service.ModeloService;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/modelos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModeloResource {

    @Inject
    ModeloService modeloService;

    @GET
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public List<ModeloResponseDTO> findAll(@QueryParam("marcaId") Long marcaId, 
                                           @QueryParam("nome") String nome) {
        if (marcaId != null) {
            return modeloService.findByMarcaId(marcaId);
        }
        if (nome != null && !nome.isBlank()) {
            return modeloService.searchByNome(nome);
        }
        return modeloService.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public ModeloResponseDTO findById(@PathParam("id") Long id) {
        return modeloService.findById(id);
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@Valid ModeloRequestDTO dto) {
        ModeloResponseDTO response = modeloService.create(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, @Valid ModeloRequestDTO dto) {
        ModeloResponseDTO response = modeloService.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {
        modeloService.delete(id);
        return Response.noContent().build();
    }
}
