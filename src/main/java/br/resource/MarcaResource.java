package br.resource;

import java.util.List;

import br.dto.MarcaRequestDTO;
import br.dto.MarcaResponseDTO;
import br.service.MarcaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
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
    MarcaService service;

    @POST
    public Response salvar(@Valid MarcaRequestDTO dto) {
        
        MarcaResponseDTO responseDTO = service.salvar(dto);
        return Response
                .status(Response.Status.CREATED)
                .entity(responseDTO)
                .build();
    }

    @GET
    public List<MarcaResponseDTO> listar() {
        
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            
            MarcaResponseDTO dto = service.buscarPorId(id);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            
            return Response.status(Response.Status.NOT_FOUND).entity("Marca não encontrada").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid MarcaRequestDTO dto) {
        try {
            
            service.atualizar(id, dto);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            
            return Response.status(Response.Status.NOT_FOUND).entity("Marca não encontrada").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            
            service.deletar(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            
            return Response.status(Response.Status.NOT_FOUND).entity("Marca não encontrada").build();
        }
    }
}
