package br.controller;

import java.util.List;

import br.dto.GabineteRequestDTO;
import br.dto.GabineteResponseDTO;
import br.service.GabineteService;
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

@Path("/gabinetes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GabineteController {
    
    @Inject
    GabineteService service;

    @POST
    public void salvar(GabineteRequestDTO dto) {
        service.salvar(dto);
    }

    @GET
    public List<GabineteResponseDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public GabineteResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorID(id);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, GabineteRequestDTO dto) {
        service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
