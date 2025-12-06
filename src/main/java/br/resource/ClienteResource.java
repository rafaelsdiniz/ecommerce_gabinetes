package br.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.dto.request.AtualizarClienteDTO;
import br.dto.request.ClienteRequestDTO;
import br.dto.request.TrocarSenhaDTO;
import br.dto.response.ClienteResponseDTO;
import br.service.ClienteService;
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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @Inject
    ClienteService clienteService;

    @Context
    SecurityContext securityContext;

    @POST
    public Response salvar(@Valid ClienteRequestDTO dto) {
        ClienteResponseDTO responseDTO = clienteService.salvar(dto);
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public List<ClienteResponseDTO> listar() {
        return clienteService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public ClienteResponseDTO buscarPorId(@PathParam("id") Long id) {
        return clienteService.buscarPorId(id);
    }

    @GET
    @Path("/cpf")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public ClienteResponseDTO buscarPorCpf(@QueryParam("cpf") String cpf) {
        return clienteService.buscarPorCpf(cpf);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response atualizar(@PathParam("id") Long id, @Valid ClienteRequestDTO dto) {
        ClienteResponseDTO atualizado = clienteService.atualizar(id, dto);
        return Response.ok(atualizado).build();
    }
    
    @GET
    @Path("/meu-perfil")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response buscarMeuPerfil() {
        String email = securityContext.getUserPrincipal().getName();
        
        if (email == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Usuário não autenticado")
                    .build();
        }
        
        ClienteResponseDTO clienteDTO = clienteService.findByEmail(email);
        if (clienteDTO == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado")
                    .build();
        }
        
        return Response.ok(clienteDTO).build();
    }
    
    @PUT
    @Path("/meu-perfil")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response atualizarMeuPerfil(@Valid AtualizarClienteDTO dto) {
        try {
            LOG.infof("Recebendo solicitação para atualizar perfil - DTO: %s", dto);
            
            String email = securityContext.getUserPrincipal().getName();
            LOG.infof("Usuário logado: %s", email);
            
            ClienteResponseDTO clienteDTO = clienteService.findByEmail(email);
            if (clienteDTO == null) {
                LOG.errorf("Cliente não encontrado para email: %s", email);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Cliente não encontrado")
                        .build();
            }
            
            LOG.infof("Atualizando cliente ID: %d", clienteDTO.id());
            ClienteResponseDTO atualizado = clienteService.atualizarPerfil(clienteDTO.id(), dto);
            
            LOG.infof("Perfil atualizado com sucesso: %s", atualizado);
            return Response.ok(atualizado).build();
            
        } catch (WebApplicationException e) {
            LOG.errorf("Erro na atualização: %s", e.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.errorf("Erro inesperado: %s", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }
    
    @PUT
    @Path("/minha-senha")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response trocarMinhaSenha(@Valid TrocarSenhaDTO dto) {
        try {
            LOG.infof("Recebendo solicitação para trocar senha");
            
            String email = securityContext.getUserPrincipal().getName();
            LOG.infof("Usuário logado: %s", email);
            
            ClienteResponseDTO clienteDTO = clienteService.findByEmail(email);
            if (clienteDTO == null) {
                LOG.errorf("Cliente não encontrado para email: %s", email);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Cliente não encontrado")
                        .build();
            }
            
            LOG.infof("Trocando senha do cliente ID: %d", clienteDTO.id());
            clienteService.alterarSenha(clienteDTO.id(), dto);
            
            LOG.infof("Senha alterada com sucesso para cliente ID: %d", clienteDTO.id());
            return Response.noContent().build();
            
        } catch (Exception e) {
            LOG.errorf("Erro ao trocar senha: %s", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response deletar(@PathParam("id") Long id) {
        clienteService.deletar(id);
        return Response.noContent().build();
    }
}