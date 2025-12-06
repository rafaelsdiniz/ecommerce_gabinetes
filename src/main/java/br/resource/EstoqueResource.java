package br.resource;

import java.util.List;

import br.dto.request.EstoqueRequestDTO;
import br.dto.response.EstoqueResponseDTO;
import br.service.EstoqueService;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/estoques")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstoqueResource {

    @Inject
    EstoqueService estoqueService;

    @Context
    SecurityContext securityContext;

    @POST
    @RolesAllowed("ADMIN")
    public Response salvar(@Valid EstoqueRequestDTO dto) {
        EstoqueResponseDTO response = estoqueService.salvar(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @Path("/gabinete/{gabineteId}/adicionar")
    @RolesAllowed("ADMIN")
    public Response adicionarEstoque(@PathParam("gabineteId") Long gabineteId, @QueryParam("quantidade") Integer quantidade) {
        EstoqueResponseDTO response = estoqueService.adicionarEstoque(gabineteId, quantidade);
        return Response.ok(response).build();
    }

    @POST
    @Path("/gabinete/{gabineteId}/remover")
    @RolesAllowed("ADMIN")
    public Response removerEstoque(@PathParam("gabineteId") Long gabineteId, @QueryParam("quantidade") Integer quantidade) {
        EstoqueResponseDTO response = estoqueService.removerEstoque(gabineteId, quantidade);
        return Response.ok(response).build();
    }

    @GET
    @Path("/gabinete/{gabineteId}/disponibilidade")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response verificarDisponibilidade(@PathParam("gabineteId") Long gabineteId, @QueryParam("quantidade") Integer quantidade) {
        boolean disponivel = estoqueService.verificarDisponibilidade(gabineteId, quantidade);
        return Response.ok().entity("{\"disponivel\": " + disponivel + "}").build();
    }

    @GET
    @Path("/gabinete/{gabineteId}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response buscarPorGabineteId(@PathParam("gabineteId") Long gabineteId) {
        EstoqueResponseDTO estoque = estoqueService.buscarPorGabineteId(gabineteId);
        return Response.ok(estoque).build();
    }

    @GET
    @Path("/baixo")
    @RolesAllowed("ADMIN")
    public Response listarEstoqueBaixo(@QueryParam("minimo") Integer quantidadeMinima) {
        Integer minimo = quantidadeMinima != null ? quantidadeMinima : 10;
        List<EstoqueResponseDTO> estoques = estoqueService.listarEstoqueBaixo(minimo);
        return Response.ok(estoques).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public List<EstoqueResponseDTO> listarTodos() {
        return estoqueService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public EstoqueResponseDTO buscarPorId(@PathParam("id") Long id) {
        return estoqueService.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public EstoqueResponseDTO atualizar(@PathParam("id") Long id, @Valid EstoqueRequestDTO dto) {
        return estoqueService.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response deletar(@PathParam("id") Long id) {
        estoqueService.deletar(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
