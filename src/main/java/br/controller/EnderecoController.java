package br.controller;

import java.util.List;

import br.dto.EnderecoRequestDTO;
import br.dto.EnderecoResponseDTO;
import br.service.EnderecoService;
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

@Path("/enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoController {

    @Inject
    EnderecoService service;

    @POST
    public void salvar(EnderecoRequestDTO dto) {
        service.salvar(dto);
    }

    @GET
    public List<EnderecoResponseDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public EnderecoResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, EnderecoRequestDTO dto) {
        service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
