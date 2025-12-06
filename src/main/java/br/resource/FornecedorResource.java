package br.resource;

import java.util.List;

import br.dto.request.FornecedorRequestDTO;
import br.dto.response.FornecedorResponseDTO;
import br.service.FornecedorService;
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

@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {

    @Inject
    FornecedorService fornecedorService;

    @GET
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public List<FornecedorResponseDTO> findAll() {
        return fornecedorService.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public FornecedorResponseDTO findById(@PathParam("id") Long id) {
        return fornecedorService.findById(id);
    }

    @GET
    @Path("/cnpj/{cnpj}")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public FornecedorResponseDTO findByCnpj(@PathParam("cnpj") String cnpj) {
        return fornecedorService.findByCnpj(cnpj);
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@Valid FornecedorRequestDTO dto) {
        FornecedorResponseDTO response = fornecedorService.create(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, @Valid FornecedorRequestDTO dto) {
        FornecedorResponseDTO response = fornecedorService.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {
        fornecedorService.delete(id);
        return Response.noContent().build();
    }
}
