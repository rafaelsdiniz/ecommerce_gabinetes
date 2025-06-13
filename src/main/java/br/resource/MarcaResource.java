package br.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.dto.MarcaRequestDTO;
import br.dto.MarcaResponseDTO;
import br.service.MarcaService;
import io.quarkus.security.identity.SecurityIdentity;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    private static final Logger LOG = Logger.getLogger(MarcaResource.class);

    @Inject
    MarcaService service;

    @Inject
    SecurityIdentity securityIdentity;

    @POST
    @RolesAllowed("Adm")
    public Response salvar(@Valid MarcaRequestDTO dto) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou criação de nova marca: nome='%s'", usuario, dto.getNome());

        MarcaResponseDTO responseDTO = service.salvar(dto);

        LOG.infof("Marca criada com sucesso: ID=%d, nome='%s'", responseDTO.getId(), responseDTO.getNome());
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed({"Adm", "User"})
    public List<MarcaResponseDTO> listar() {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou listagem de todas as marcas", usuario);
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response buscarPorId(@PathParam("id") Long id) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou marca por ID: %d", usuario, id);

        try {
            MarcaResponseDTO dto = service.buscarPorId(id);
            LOG.infof("Marca encontrada: ID=%d, nome='%s'", dto.getId(), dto.getNome());
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            LOG.warnf("Marca ID=%d não encontrada", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Marca não encontrada").build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response atualizar(@PathParam("id") Long id, @Valid MarcaRequestDTO dto) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou atualização da marca ID=%d para nome='%s'", usuario, id, dto.getNome());

        try {
            service.atualizar(id, dto);
            LOG.infof("Marca ID=%d atualizada com sucesso", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warnf("Falha ao atualizar: marca ID=%d não encontrada", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Marca não encontrada").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response deletar(@PathParam("id") Long id) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou exclusão da marca ID=%d", usuario, id);

        try {
            service.deletar(id);
            LOG.infof("Marca ID=%d excluída com sucesso", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warnf("Falha ao excluir: marca ID=%d não encontrada", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Marca não encontrada").build();
        }
    }
}
