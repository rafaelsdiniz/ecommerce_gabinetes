package br.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.UUID;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@ApplicationScoped
public class FileStorageService {

    @Inject
    S3Client s3Client;

    @ConfigProperty(name = "minio.bucket")
    String bucketName;

    @ConfigProperty(name = "quarkus.minio.url", defaultValue = "http://localhost:9000")
    String minioUrl;

    // Método para compatibilidade (3 parâmetros)
    public String uploadFile(InputStream inputStream, String fileName, String contentType) {
        System.out.println("⚡ uploadFile(3 params) chamado para: " + fileName);
        return uploadFile(inputStream, fileName, contentType, -1L);
    }

    // Método principal (4 parâmetros)
    public String uploadFile(InputStream inputStream, String fileName, String contentType, Long fileSize) {
        try {
            System.out.println("=================================================================");
            System.out.println("📤 INICIANDO UPLOAD PARA MINIO");
            System.out.println("   Bucket: " + bucketName);
            System.out.println("   Arquivo: " + fileName);
            System.out.println("   Content-Type: " + contentType);
            System.out.println("   Tamanho informado: " + (fileSize != null ? fileSize + " bytes" : "não informado"));
            System.out.println("=================================================================");
            
            // Gera chave única para o arquivo
            String fileKey = generateFileKey(fileName);
            System.out.println("   Chave gerada: " + fileKey);
            
            // Se não tiver tamanho, lê o stream para determinar
            InputStream streamToUpload = inputStream;
            long finalFileSize = fileSize != null ? fileSize : -1L;
            
            if (finalFileSize <= 0) {
                System.out.println("   Determinando tamanho do arquivo...");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[8192];
                int len;
                long totalBytes = 0;
                
                while ((len = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                    totalBytes += len;
                }
                
                byte[] bytes = baos.toByteArray();
                finalFileSize = totalBytes;
                streamToUpload = new ByteArrayInputStream(bytes);
                System.out.println("   Tamanho determinado: " + finalFileSize + " bytes");
            }
            
            // Prepara requisição
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .contentType(contentType != null ? contentType : "application/octet-stream")
                    .build();
            
            System.out.println("   Enviando para MinIO...");
            
            // Faz upload
            s3Client.putObject(putObjectRequest, 
                RequestBody.fromInputStream(streamToUpload, finalFileSize));
            
            System.out.println("✅✅✅ UPLOAD CONCLUÍDO COM SUCESSO!");
            System.out.println("   Chave: " + fileKey);
            System.out.println("   Tamanho enviado: " + finalFileSize + " bytes");
            System.out.println("=================================================================");
            
            return fileKey;
            
        } catch (Exception e) {
            System.err.println("❌❌❌ ERRO NO UPLOAD: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao fazer upload para MinIO: " + e.getMessage(), e);
        }
    }

    private String generateFileKey(String originalFileName) {
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        
        String extension = "";
        if (originalFileName != null) {
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < originalFileName.length() - 1) {
                extension = originalFileName.substring(dotIndex).toLowerCase();
            }
        }
        
        return String.format("%s_%s%s", timestamp, uuid, extension);
    }

    // MÉTODO GETFILEURL CORRIGIDO
    public String getFileUrl(String fileKey) {
        try {
            System.out.println("=== DEBUG getFileUrl ===");
            System.out.println("Config minioUrl: " + minioUrl);
            System.out.println("Bucket: " + bucketName);
            System.out.println("FileKey: " + fileKey);
            
            // CORREÇÃO CRÍTICA: Garantir que a URL está correta
            String baseUrl = minioUrl;
            
            // Remove barra final se tiver
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
            
            // Monta URL correta para MinIO público
            String url = baseUrl + "/" + bucketName + "/" + fileKey;
            
            System.out.println("URL final gerada: " + url);
            System.out.println("=== FIM DEBUG ===");
            
            return url;
            
        } catch (Exception e) {
            System.err.println("❌ Erro no getFileUrl: " + e.getMessage());
            // Fallback direto
            return "http://localhost:9000/" + bucketName + "/" + fileKey;
        }
    }

    public void deleteFile(String fileKey) {
        try {
            System.out.println("🗑️  Deletando arquivo: " + fileKey);
            
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            
            System.out.println("✅ Arquivo deletado com sucesso!");
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao deletar arquivo: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar arquivo: " + e.getMessage(), e);
        }
    }
}