package br.resource;

import java.util.List;

import br.dto.ItemPedidoRequestDTO;
import br.dto.ItemPedidoResponseDTO;
import br.service.ItemPedidoService;
import io.quarkus.security.identity.SecurityIdentity;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/itempedido")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemPedidoResource {

    private static final Logger LOG = Logger.getLogger(ItemPedidoResource.class);

    @Inject
    ItemPedidoService service;

    @Inject
    SecurityIdentity securityIdentity;

    @POST
    @RolesAllowed("Adm")
    public Response salvar(@Valid ItemPedidoRequestDTO dto) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou criação de item de pedido: idpedido=%d, idgabinete=%d, quantidade=%d",
                usuario, dto.getIdPedido(), dto.getIdGabinete(), dto.getQuantidade());

        ItemPedidoResponseDTO responseDTO = service.criar(dto);

        LOG.infof("Item de pedido criado com sucesso: ID=%d, ", 
                responseDTO.getId());

        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed({"Adm", "User"})
    public List<ItemPedidoResponseDTO> listarTodos() {
        String usuario = securityIdentity.getPrincipal().getName();
        if (securityIdentity.hasRole("Adm")) {
            LOG.infof("Usuário '%s' (Adm) solicitou listagem de todos os itens de pedido", usuario);
            return service.listarTodos();
        } else {
            LOG.infof("Usuário '%s' (User) solicitou seus próprios itens de pedido", usuario);
            return service.listarPorClienteLogado();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Adm")
    public ItemPedidoResponseDTO buscarPorId(@PathParam("id") Long id) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou item de pedido por ID: %d", usuario, id);

        ItemPedidoResponseDTO item = service.buscarPorId(id);
        LOG.infof("Item de pedido encontrado: ID=%d", item.getId());
        return item;
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response atualizar(@PathParam("id") Long id, @Valid ItemPedidoRequestDTO dto) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou atualização do item de pedido ID=%d: nova quantidade=%d", 
                usuario, id, dto.getQuantidade());

        service.atualizar(id, dto);
        LOG.infof("Item de pedido ID=%d atualizado com sucesso", id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response deletar(@PathParam("id") Long id) {
        String usuario = securityIdentity.getPrincipal().getName();
        LOG.infof("Usuário '%s' solicitou exclusão do item de pedido ID=%d", usuario, id);

        service.deletar(id);
        LOG.infof("Item de pedido ID=%d excluído com sucesso", id);
        return Response.noContent().build();
    }
}
