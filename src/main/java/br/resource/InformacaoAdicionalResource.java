package br.resource;

import java.util.List;

import br.dto.request.InformacaoAdicionalRequestDTO;
import br.dto.response.InformacaoAdicionalResponseDTO;
import br.service.InformacaoAdicionalService;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/informacoes-adicionais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InformacaoAdicionalResource {

    @Inject
    InformacaoAdicionalService infoService;

    @GET
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public List<InformacaoAdicionalResponseDTO> findAll(@QueryParam("gabineteId") Long gabineteId) {
        if (gabineteId != null) {
            return infoService.findByGabineteId(gabineteId);
        }
        return infoService.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public InformacaoAdicionalResponseDTO findById(@PathParam("id") Long id) {
        return infoService.findById(id);
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@Valid InformacaoAdicionalRequestDTO dto) {
        InformacaoAdicionalResponseDTO response = infoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, @Valid InformacaoAdicionalRequestDTO dto) {
        InformacaoAdicionalResponseDTO response = infoService.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {
        infoService.delete(id);
        return Response.noContent().build();
    }
}

