package br.resource;

import java.util.List;

import br.dto.request.MarcaRequestDTO;
import br.dto.response.MarcaResponseDTO;
import br.service.MarcaService;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService marcaService;

    @GET
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public List<MarcaResponseDTO> findAll() {
        return marcaService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public MarcaResponseDTO findById(@PathParam("id") Long id) {
        return marcaService.buscarPorId(id);
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@Valid MarcaRequestDTO dto) {
        MarcaResponseDTO response = marcaService.salvar(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, @Valid MarcaRequestDTO dto) {
        MarcaResponseDTO response = marcaService.atualizar(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {
        marcaService.deletar(id);
        return Response.noContent().build();
    }
}
