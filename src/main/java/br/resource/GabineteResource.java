package br.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.dto.GabineteRequestDTO;
import br.dto.GabineteResponseDTO;
import br.service.GabineteService;
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

@Path("/gabinetes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GabineteResource {

    private static final Logger LOG = Logger.getLogger(GabineteResource.class);

    @Inject
    GabineteService service;

    @Context
    SecurityContext securityContext;

    @POST
    @RolesAllowed("Adm")
    public Response salvar(@Valid GabineteRequestDTO dto) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou criação de gabinete: nome='%s', marcaId=%d", 
                  usuario, dto.getNomeExibicao(), dto.getMarca());

        GabineteResponseDTO responseDTO = service.salvar(dto);

        LOG.infof("Gabinete criado com sucesso: ID=%d, nome='%s'", 
                  responseDTO.getId(), responseDTO.getNomeExibicao());

        return Response
            .status(Response.Status.CREATED)
            .entity(responseDTO)
            .build();
    }   

    @GET
    @RolesAllowed({"Adm", "User"})
    public List<GabineteResponseDTO> listar() {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou listagem de todos os gabinetes", usuario);
        return service.listarTodos();
    }
    
    @GET
    @Path("/{id}")
    @RolesAllowed("Adm")
    public GabineteResponseDTO buscarPorId(@PathParam("id") Long id) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou gabinete por ID: %d", usuario, id);

        GabineteResponseDTO gabinete = service.buscarPorId(id);
        LOG.infof("Gabinete encontrado: ID=%d, nome='%s'", gabinete.getId(), gabinete.getNomeExibicao());
        return gabinete;
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public void atualizar(@PathParam("id") Long id, @Valid GabineteRequestDTO dto) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou atualização do gabinete ID=%d: novo nome='%s'", 
                  usuario, id, dto.getNomeExibicao());

        service.atualizar(id, dto);
        LOG.infof("Gabinete ID=%d atualizado com sucesso", id);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public void deletar(@PathParam("id") Long id) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou exclusão do gabinete ID=%d", usuario, id);

        service.deletar(id);
        LOG.infof("Gabinete ID=%d excluído com sucesso", id);
    }
}
