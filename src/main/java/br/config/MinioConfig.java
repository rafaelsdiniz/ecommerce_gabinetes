package br.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@ApplicationScoped
public class MinioConfig {

    @ConfigProperty(name = "minio.url")
    String minioUrl;

    @ConfigProperty(name = "minio.access-key")
    String accessKey;

    @ConfigProperty(name = "minio.secret-key")
    String secretKey;

    @Produces
    @ApplicationScoped
    public S3Client s3Client() {
        try {
            System.out.println("=================================================================");
            System.out.println("🚀 CONFIGURANDO S3CLIENT PARA MINIO");
            System.out.println("   URL: " + minioUrl);
            System.out.println("   Access Key: " + accessKey);
            System.out.println("   Secret Key: " + (secretKey != null ? "***" + secretKey.substring(secretKey.length() - 3) : "null"));
            System.out.println("=================================================================");
            
            return S3Client.builder()
                    .endpointOverride(URI.create(minioUrl))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)
                    ))
                    .region(Region.US_EAST_1)
                    .serviceConfiguration(S3Configuration.builder()
                            .pathStyleAccessEnabled(true)
                            .chunkedEncodingEnabled(false)
                            .build())
                    .build();
                    
        } catch (Exception e) {
            System.err.println("❌❌❌ ERRO CRÍTICO AO CRIAR S3CLIENT: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao configurar MinIO: " + e.getMessage(), e);
        }
    }
}