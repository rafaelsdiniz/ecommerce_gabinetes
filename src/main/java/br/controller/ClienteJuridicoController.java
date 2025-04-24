package br.controller;

import java.util.List;

import br.dto.ClienteJuridicoRequestDTO;
import br.dto.ClienteJuridicoResponseDTO;
import br.service.ClienteJuridicoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/clientes/juridicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteJuridicoController {

    @Inject
    ClienteJuridicoService service;

    @POST
    public void salvar(ClienteJuridicoRequestDTO dto) {
        service.salvar(dto);
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

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
