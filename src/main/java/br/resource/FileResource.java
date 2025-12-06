package br.resource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.service.FileStorageService;
import br.service.MinioTestService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/files")
@Produces(MediaType.APPLICATION_JSON)
public class FileResource {

    @Inject
    FileStorageService fileStorageService;

    @Inject
    MinioTestService minioTestService;

    @GET
    @Path("/test")
    @PermitAll
    public Response testMinIO() {
        String result = minioTestService.testConnection();
        return Response.ok(Map.of("message", result)).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @PermitAll
    public Response uploadFile(MultipartFormDataInput input) {
        try {
            System.out.println("📤 Recebendo upload via FileResource...");
            
            Map<String, List<InputPart>> formDataMap = input.getFormDataMap();
            
            // Obter arquivo
            List<InputPart> fileParts = formDataMap.get("file");
            if (fileParts == null || fileParts.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Arquivo é obrigatório"))
                        .build();
            }
            
            InputPart filePart = fileParts.get(0);
            InputStream fileStream = filePart.getBody(InputStream.class, null);
            
            // Obter o tamanho do arquivo do Content-Length header
            String contentLengthHeader = filePart.getHeaders().getFirst("Content-Length");
            Long fileSize = null;
            if (contentLengthHeader != null) {
                try {
                    fileSize = Long.parseLong(contentLengthHeader);
                } catch (NumberFormatException e) {
                    // Ignorar se não for um número válido
                }
            }
            
            // Obter nome do arquivo
            String fileName = null;
            
            // Tenta pegar do campo 'fileName'
            List<InputPart> fileNameParts = formDataMap.get("fileName");
            if (fileNameParts != null && !fileNameParts.isEmpty()) {
                fileName = fileNameParts.get(0).getBody(String.class, null);
            }
            
            // Se não tiver nome, tenta extrair do Content-Disposition
            if (fileName == null || fileName.isBlank()) {
                String contentDisposition = filePart.getHeaders().getFirst("Content-Disposition");
                if (contentDisposition != null) {
                    for (String param : contentDisposition.split(";")) {
                        if (param.trim().startsWith("filename")) {
                            fileName = param.split("=")[1].trim().replace("\"", "");
                            break;
                        }
                    }
                }
            }
            
            // Obter content type
            String contentType = filePart.getHeaders().getFirst("Content-Type");
            
            List<InputPart> contentTypeParts = formDataMap.get("contentType");
            if (contentTypeParts != null && !contentTypeParts.isEmpty()) {
                String customContentType = contentTypeParts.get(0).getBody(String.class, null);
                if (customContentType != null && !customContentType.isBlank()) {
                    contentType = customContentType;
                }
            }

            if (fileName == null || fileName.isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Nome do arquivo é obrigatório"))
                        .build();
            }

            // Se não tiver tamanho, usa -1 (o serviço vai determinar)
            if (fileSize == null || fileSize <= 0) {
                fileSize = -1L;
            }

            System.out.println("   Nome: " + fileName);
            System.out.println("   Tipo: " + contentType);
            System.out.println("   Tamanho: " + (fileSize > 0 ? fileSize + " bytes" : "desconhecido"));
            
            // CORREÇÃO AQUI: Passe todos os 4 parâmetros
            String fileKey = fileStorageService.uploadFile(fileStream, fileName, contentType, fileSize);
            
            String fileUrl = fileStorageService.getFileUrl(fileKey);

            Map<String, String> response = new HashMap<>();
            response.put("fileKey", fileKey);
            response.put("fileUrl", fileUrl);
            response.put("fileName", fileName);
            response.put("contentType", contentType);
            response.put("fileSize", fileSize.toString());
            response.put("message", "Upload realizado com sucesso");

            return Response.ok(response).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro ao fazer upload: " + e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{fileKey}")
    public Response deleteFile(@PathParam("fileKey") String fileKey) {
        try {
            fileStorageService.deleteFile(fileKey);
            return Response.ok(Map.of("message", "Arquivo deletado com sucesso")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro ao deletar arquivo: " + e.getMessage()))
                    .build();
        }
    }
}