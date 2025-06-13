package br.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.dto.EstoqueRequestDTO;
import br.dto.EstoqueResponseDTO;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/estoques")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstoqueResource {

    private static final Logger LOG = Logger.getLogger(EstoqueResource.class);

    @Inject
    EstoqueService estoqueService;

    @Context
    SecurityContext securityContext;

    @POST
    @RolesAllowed("Adm")
    public Response salvar(@Valid EstoqueRequestDTO dto) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou criação de estoque: produtoId=%d, quantidade=%d", usuario, dto.getGabineteId(), dto.getQuantidadeDisponivel());

        EstoqueResponseDTO response = estoqueService.salvar(dto);

        LOG.infof("Estoque criado com sucesso: ID=%d, produtoId=%d, quantidade=%d", response.getId(), response.getGabineteId(), response.getQuantidadeDisponivel());
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @RolesAllowed("Adm")
    public List<EstoqueResponseDTO> listarTodos() {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou listagem de estoques", usuario);
        return estoqueService.listarTodos();
    }

    @GET
    @RolesAllowed("Adm")
    @Path("/{id}")
    public EstoqueResponseDTO buscarPorId(@PathParam("id") Long id) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou estoque por ID: %d", usuario, id);

        EstoqueResponseDTO estoque = estoqueService.buscarPorId(id);
        LOG.infof("Estoque encontrado: ID=%d, produtoId=%d, quantidade=%d", estoque.getId(), estoque.getGabineteId(), estoque.getQuantidadeDisponivel());
        return estoque;
    }

    @PUT
    @RolesAllowed("Adm")
    @Path("/{id}")
    public EstoqueResponseDTO atualizar(@PathParam("id") Long id, @Valid EstoqueRequestDTO dto) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou atualização de estoque ID=%d: novo produtoId=%d, nova quantidade=%d", usuario, id, dto.getGabineteId(), dto.getQuantidadeDisponivel());

        EstoqueResponseDTO atualizado = estoqueService.atualizar(id, dto);

        LOG.infof("Estoque atualizado com sucesso: ID=%d", atualizado.getId());
        return atualizado;
    }

    @DELETE
    @RolesAllowed("Adm")
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou exclusão de estoque ID=%d", usuario, id);

        estoqueService.deletar(id);

        LOG.infof("Estoque excluído com sucesso: ID=%d", id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
