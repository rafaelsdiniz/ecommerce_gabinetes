package br.resource;

import java.util.List;

import br.dto.CategoriaRequestDTO;
import br.dto.CategoriaResponseDTO;
import br.service.CategoriaService;
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

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService service;

    @POST
    public Response salvar(@Valid CategoriaRequestDTO dto) {
        CategoriaResponseDTO responseDTO = service.salvar(dto);
        return Response
                .status(Response.Status.CREATED)
                .entity(responseDTO)
                .build();  
    }

    @GET
    public List<CategoriaResponseDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            CategoriaResponseDTO categoria = service.buscarPorId(id);
            return Response
                .status(Response.Status.OK)
                .entity(categoria)
                .build();  
        } catch (NotFoundException e) {
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity("Categoria não encontrada")
                .build();  
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid CategoriaRequestDTO dto) {
        try {
            service.atualizar(id, dto);
            return Response.status(Response.Status.NO_CONTENT).build();  
        } catch (NotFoundException e) {
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity("Categoria não encontrada para atualização")
                .build();  
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            service.deletar(id);
            return Response.status(Response.Status.NO_CONTENT).build();  
        } catch (NotFoundException e) {
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity("Categoria não encontrada para exclusão")
                .build();  
        }
    }
}
