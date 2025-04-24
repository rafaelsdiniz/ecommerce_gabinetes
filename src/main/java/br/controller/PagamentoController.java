package br.controller;

import java.util.List;

import br.dto.PagamentoRequestDTO;
import br.dto.PagamentoResponseDTO;
import br.service.PagamentoService;
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

@Path("/pagamento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoController {

    @Inject
    PagamentoService service;

    @POST
    public void salvar(PagamentoRequestDTO dto) {
        service.salvar(dto);
    }

    @GET
    public List<PagamentoResponseDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public PagamentoResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, PagamentoRequestDTO dto) {
        service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
