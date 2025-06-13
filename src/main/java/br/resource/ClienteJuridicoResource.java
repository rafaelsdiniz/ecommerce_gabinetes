package br.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.dto.ClienteJuridicoRequestDTO;
import br.dto.ClienteJuridicoResponseDTO;
import br.service.ClienteJuridicoService;
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

@Path("/clientes/juridicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteJuridicoResource {

    private static final Logger LOG = Logger.getLogger(ClienteJuridicoResource.class);

    @Inject
    ClienteJuridicoService service;

    @Context
    SecurityContext securityContext;

    @POST
    @RolesAllowed("Adm")
    public Response salvar(@Valid ClienteJuridicoRequestDTO dto) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou salvar novo cliente jurídico: razaoSocial=%s, cnpj=%s", usuario, dto.getRazaoSocial(), dto.getCnpj());

        ClienteJuridicoResponseDTO responseDTO = service.salvar(dto);

        LOG.infof("Cliente jurídico salvo com sucesso: ID=%d, Razão Social=%s", responseDTO.getId(), responseDTO.getRazaoSocial());
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed("Adm")
    public List<ClienteJuridicoResponseDTO> listar() {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou listagem de todos os clientes jurídicos", usuario);
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Adm")
    public ClienteJuridicoResponseDTO buscarPorId(@PathParam("id") Long id) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou cliente jurídico por ID: %d", usuario, id);

        ClienteJuridicoResponseDTO cliente = service.buscarPorId(id);
        LOG.infof("Cliente jurídico encontrado: ID=%d, Razão Social=%s", cliente.getId(), cliente.getRazaoSocial());

        return cliente;
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public void atualizar(@PathParam("id") Long id, @Valid ClienteJuridicoRequestDTO dto) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou atualização do cliente jurídico ID=%d: razaoSocial=%s, cnpj=%s", usuario, id, dto.getRazaoSocial(), dto.getCnpj());

        service.atualizar(id, dto);
        LOG.infof("Cliente jurídico atualizado com sucesso: ID=%d", id);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public void deletar(@PathParam("id") Long id) {
        String usuario = securityContext.getUserPrincipal().getName();
        LOG.infof("Usuário '%s' requisitou exclusão do cliente jurídico ID=%d", usuario, id);

        service.deletar(id);
        LOG.infof("Cliente jurídico excluído com sucesso: ID=%d", id);
    }
}
