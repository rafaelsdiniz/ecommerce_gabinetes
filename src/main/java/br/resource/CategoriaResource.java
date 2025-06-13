package br.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.dto.CategoriaRequestDTO;
import br.dto.CategoriaResponseDTO;
import br.service.CategoriaService;
import jakarta.annotation.security.RolesAllowed;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    private static final Logger LOG = Logger.getLogger(CategoriaResource.class);

    @Inject
    CategoriaService service;

    @Context
    SecurityContext securityContext;

    @POST
    @RolesAllowed("Adm")
    public Response salvar(@Valid CategoriaRequestDTO dto) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou salvar nova categoria: %s", usuario, dto.getNome());

        CategoriaResponseDTO responseDTO = service.salvar(dto);

        LOG.infof("Categoria criada com sucesso por '%s': ID=%d, Nome=%s", usuario, responseDTO.getId(), responseDTO.getNome());
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();  
    }

    @GET
    @RolesAllowed({"Adm", "User"})
    public List<CategoriaResponseDTO> listar() {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou listagem de todas as categorias", usuario);
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response buscarPorId(@PathParam("id") Long id) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou buscar categoria por ID: %d", usuario, id);

        try {
            CategoriaResponseDTO categoria = service.buscarPorId(id);
            LOG.infof("Categoria encontrada: ID=%d, Nome=%s", categoria.getId(), categoria.getNome());
            return Response.status(Response.Status.OK).entity(categoria).build();  
        } catch (NotFoundException e) {
            LOG.warnf("Categoria com ID=%d não encontrada", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria não encontrada").build();  
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response atualizar(@PathParam("id") Long id, @Valid CategoriaRequestDTO dto) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou atualizar categoria ID=%d com dados: %s", usuario, id, dto.getNome());

        try {
            service.atualizar(id, dto);
            LOG.infof("Categoria atualizada com sucesso: ID=%d", id);
            return Response.status(Response.Status.NO_CONTENT).build();  
        } catch (NotFoundException e) {
            LOG.warnf("Categoria com ID=%d não encontrada para atualização", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria não encontrada para atualização").build();  
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response deletar(@PathParam("id") Long id) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou deletar categoria ID=%d", usuario, id);

        try {
            service.deletar(id);
            LOG.infof("Categoria deletada com sucesso: ID=%d", id);
            return Response.status(Response.Status.NO_CONTENT).build();  
        } catch (NotFoundException e) {
            LOG.warnf("Categoria com ID=%d não encontrada para exclusão", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria não encontrada para exclusão").build();  
        }
    }
}
