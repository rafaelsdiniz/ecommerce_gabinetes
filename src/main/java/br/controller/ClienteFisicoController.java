package br.controller;

import java.util.List;

import br.dto.ClienteFisicoRequestDTO;
import br.dto.ClienteFisicoResponseDTO;
import br.service.ClienteFisicoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/clientes/fisicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteFisicoController {

    @Inject
    ClienteFisicoService service;

    @POST
    public void salvar(ClienteFisicoRequestDTO dto) {
        service.salvar(dto);
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

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
