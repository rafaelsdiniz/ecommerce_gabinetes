package br.resource;

import java.util.List;

import br.dto.ClienteFisicoRequestDTO;
import br.dto.ClienteFisicoResponseDTO;
import br.service.ClienteFisicoService;
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
import org.jboss.logging.Logger;

@Path("/clientes/fisicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteFisicoResource {

    private static final Logger LOG = Logger.getLogger(ClienteFisicoResource.class);

    @Inject
    ClienteFisicoService service;

    @Context
    SecurityContext securityContext;

    @POST
    @RolesAllowed("Adm")
    public Response salvar(@Valid ClienteFisicoRequestDTO dto) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou salvar novo cliente físico: nome=%s, cpf=%s", usuario, dto.getNome(), dto.getCpf());

        ClienteFisicoResponseDTO responseDTO = service.salvar(dto);

        LOG.infof("Cliente físico salvo com sucesso: ID=%d, Nome=%s", responseDTO.getId(), responseDTO.getNome());
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed({"Adm"})
    public List<ClienteFisicoResponseDTO> listar() {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou listagem de todos os clientes físicos", usuario);
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Adm")
    public ClienteFisicoResponseDTO buscarPorId(@PathParam("id") Long id) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou cliente físico por ID: %d", usuario, id);

        ClienteFisicoResponseDTO cliente = service.buscarPorId(id);
        LOG.infof("Cliente físico encontrado: ID=%d, Nome=%s", cliente.getId(), cliente.getNome());

        return cliente;
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public void atualizar(@PathParam("id") Long id, @Valid ClienteFisicoRequestDTO dto) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou atualização do cliente físico ID=%d: nome=%s, cpf=%s", usuario, id, dto.getNome(), dto.getCpf());

        service.atualizar(id, dto);
        LOG.infof("Cliente físico atualizado com sucesso: ID=%d", id);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public void deletar(@PathParam("id") Long id) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou exclusão do cliente físico ID=%d", usuario, id);

        service.deletar(id);
        LOG.infof("Cliente físico excluído com sucesso: ID=%d", id);
    }
}
