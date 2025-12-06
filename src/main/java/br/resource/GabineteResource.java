package br.resource;

import java.io.InputStream;
import java.util.List;

import br.dto.request.GabineteRequestDTO;
import br.dto.response.GabineteResponseDTO;
import br.service.FileStorageService;
import br.service.GabineteService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/gabinetes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GabineteResource {

    @Inject
    GabineteService service;

    @Inject
    FileStorageService fileStorageService;

    @Context
    SecurityContext securityContext;

    // ============================================
    // CRIAÇÃO
    // ============================================
    @POST
    @RolesAllowed("ADMIN")
    public Response salvar(@Valid GabineteRequestDTO dto) {
        try {
            GabineteResponseDTO responseDTO = service.salvar(dto);
            return Response.status(Response.Status.CREATED).entity(responseDTO).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // ============================================
    // LISTAGEM GERAL (LIBERADO)
    // ============================================
    @GET
    @PermitAll
    public List<GabineteResponseDTO> listar() {
        return service.listarTodos();
    }
    // Adicione este método no GabineteResource.java
    @POST
    @Path("/criar-com-imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed("ADMIN")
    public Response criarComImagem(@FormParam("nomeExibicao") String nomeExibicao,
                                @FormParam("marca") String marca,
                                @FormParam("preco") Double preco,
                                @FormParam("cor") String cor,
                                @FormParam("formato") String formato,
                                @FormParam("altura") Integer altura,
                                @FormParam("largura") Integer largura,
                                @FormParam("peso") Double peso,
                                @FormParam("tamanhoMaxGpu") Integer tamanhoMaxGpu,
                                @FormParam("alturaMaxCooler") Integer alturaMaxCooler,
                                @FormParam("qtdRgb") Integer qtdRgb,
                                @FormParam("usb") Integer usb,
                                @FormParam("usbc") Integer usbc,
                                @FormParam("descricao") String descricao,
                                @FormParam("categoriasIds") List<Long> categoriasIds,
                                @FormParam("file") InputStream file,
                                @FormParam("fileName") String fileName) {
        try {
            // Faz upload da imagem primeiro
            String contentType = determinarContentType(fileName);
            String imagemKey = fileStorageService.uploadFile(file, fileName, contentType, -1L);
            
            // Cria o DTO com a chave da imagem
            GabineteRequestDTO dto = new GabineteRequestDTO(
                nomeExibicao, marca, preco, cor, formato, altura, largura, peso,
                tamanhoMaxGpu, alturaMaxCooler, qtdRgb, usb, usbc, descricao,
                imagemKey, categoriasIds
            );
            
            // Salva o gabinete
            GabineteResponseDTO responseDTO = service.salvar(dto);
            return Response.status(Response.Status.CREATED).entity(responseDTO).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao criar gabinete com imagem: " + e.getMessage() + "\"}")
                    .build();
        }
    }
    // ============================================
    // BUSCAS ESPECÍFICAS
    // ============================================

    @GET
    @Path("/{id}")
    @PermitAll
    public GabineteResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @GET
    @Path("/buscar/marca")
    @PermitAll
    public List<GabineteResponseDTO> buscarPorMarca(@QueryParam("marca") String marca) {
        return service.buscarPorMarca(marca);
    }

    @GET
    @Path("/buscar/preco")
    @PermitAll
    public List<GabineteResponseDTO> buscarPorFaixaPreco(
            @QueryParam("min") Double precoMin,
            @QueryParam("max") Double precoMax) {
        return service.buscarPorFaixaPreco(precoMin, precoMax);
    }

    @GET
    @Path("/buscar/cor")
    @PermitAll
    public List<GabineteResponseDTO> buscarPorCor(@QueryParam("cor") String cor) {
        return service.buscarPorCor(cor);
    }

    @GET
    @Path("/buscar/formato")
    @PermitAll
    public List<GabineteResponseDTO> buscarPorFormato(@QueryParam("formato") String formato) {
        return service.buscarPorFormato(formato);
    }

    // ============================================
    // BUSCA POR NOME (LIBERADO)
    // ============================================
    @GET
    @PermitAll
    @Path("/buscar/nome")
    public List<GabineteResponseDTO> buscarPorNome(@QueryParam("nome") String nome) {
        return service.buscarPorNome(nome);
    }

    @GET
    @Path("/buscar/categoria/{categoriaId}")
    @PermitAll
    public List<GabineteResponseDTO> buscarPorCategoria(@PathParam("categoriaId") Long categoriaId) {
        return service.buscarPorCategoria(categoriaId);
    }

    @GET
    @Path("/ordenar/preco")
    @PermitAll
    public List<GabineteResponseDTO> listarOrdenadoPorPreco(@QueryParam("crescente") boolean crescente) {
        return service.listarOrdenadoPorPreco(crescente);
    }

    @GET
    @Path("/ordenar/nome")
    @PermitAll
    public List<GabineteResponseDTO> listarOrdenadoPorNome() {
        return service.listarOrdenadoPorNome();
    }

    // ============================================
    // ATUALIZAÇÃO E REMOÇÃO
    // ============================================
    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response atualizar(@PathParam("id") Long id, @Valid GabineteRequestDTO dto) {
        try {
            GabineteResponseDTO atualizado = service.atualizar(id, dto);
            return Response.ok(atualizado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response deletar(@PathParam("id") Long id) {
        service.deletar(id);
        return Response.noContent().build();
    }

    // ============================================
    // ENDPOINTS DE IMAGEM COM MINIO
    // ============================================

    // UPLOAD DE IMAGEM PARA GABINETE EXISTENTE
    @POST
    @Path("/{id}/imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed("ADMIN")
    public Response uploadImagem(@PathParam("id") Long id,
                                @FormParam("file") InputStream file,
                                @FormParam("fileName") String fileName) {
        try {
            System.out.println("📤 Recebendo upload de imagem para gabinete ID: " + id);
            System.out.println("   Nome do arquivo: " + fileName);
            
            // Verifica se o gabinete existe
            service.buscarPorId(id);
            
            // Determina o content type baseado na extensão do arquivo
            String contentType = determinarContentType(fileName);
            
            // Faz upload para o MinIO
            String imagemKey = fileStorageService.uploadFile(file, fileName, contentType, -1L);
            
            System.out.println("✅ Upload concluído. Chave: " + imagemKey);
            
            // Atualiza o gabinete com a nova chave
            GabineteResponseDTO response = service.atualizarImagem(id, imagemKey);
            
            return Response.ok(response).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Gabinete não encontrado\"}")
                    .build();
        } catch (Exception e) {
            System.err.println("❌ Erro no upload: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao fazer upload da imagem: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    // Método auxiliar para determinar content type
    private String determinarContentType(String fileName) {
        if (fileName == null) {
            return "application/octet-stream";
        }
        
        String lowerFileName = fileName.toLowerCase();
        if (lowerFileName.endsWith(".jpg") || lowerFileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerFileName.endsWith(".png")) {
            return "image/png";
        } else if (lowerFileName.endsWith(".gif")) {
            return "image/gif";
        } else if (lowerFileName.endsWith(".webp")) {
            return "image/webp";
        } else if (lowerFileName.endsWith(".bmp")) {
            return "image/bmp";
        } else {
            return "application/octet-stream";
        }
    }

    // DELETAR IMAGEM DO GABINETE
    @DELETE
    @Path("/{id}/imagem")
    @RolesAllowed("ADMIN")
    public Response deletarImagem(@PathParam("id") Long id) {
        try {
            GabineteResponseDTO gabinete = service.buscarPorId(id);
            
            if (gabinete.imagemKey() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Gabinete não possui imagem\"}")
                        .build();
            }

            // Deleta do MinIO
            fileStorageService.deleteFile(gabinete.imagemKey());
            
            // Atualiza o gabinete removendo a chave da imagem
            GabineteRequestDTO dtoAtualizado = new GabineteRequestDTO(
                gabinete.nomeExibicao(), gabinete.marca(), gabinete.preco(),
                gabinete.cor(), gabinete.formato(), gabinete.altura(),
                gabinete.largura(), gabinete.peso(), gabinete.tamanhoMaxGpu(),
                gabinete.alturaMaxCooler(), gabinete.qtdRgb(), gabinete.usb(),
                gabinete.usbc(), gabinete.descricao(), null, null
            );
            
            GabineteResponseDTO response = service.atualizar(id, dtoAtualizado);
            
            return Response.ok(response).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao deletar imagem: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    // OBTER URL DA IMAGEM
    @GET
    @Path("/{id}/imagem/url")
    @PermitAll
    public Response obterUrlImagem(@PathParam("id") Long id) {
        try {
            GabineteResponseDTO gabinete = service.buscarPorId(id);
            
            if (gabinete.imagemUrl() == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Imagem não encontrada\"}")
                        .build();
            }
            
            return Response.ok("{\"url\": \"" + gabinete.imagemUrl() + "\"}").build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao obter URL da imagem: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}