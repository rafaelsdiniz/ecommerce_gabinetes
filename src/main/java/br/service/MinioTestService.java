package br.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@ApplicationScoped
public class MinioTestService {

    @Inject
    S3Client s3Client;

    @ConfigProperty(name = "minio.bucket")
    String bucketName;

    @ConfigProperty(name = "minio.url")
    String minioUrl;

    public String testConnection() {
        try {
            // Testa listagem de buckets
            ListBucketsResponse response = s3Client.listBuckets();
            int bucketCount = response.buckets().size();
            
            // Testa se o bucket específico existe
            String bucketStatus = "não verificado";
            try {
                s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
                bucketStatus = "existe";
            } catch (S3Exception e) {
                if (e.statusCode() == 404) {
                    bucketStatus = "não existe";
                } else {
                    bucketStatus = "erro: " + e.getMessage();
                }
            }
            
            return String.format(
                "✓ Conexão com MinIO estabelecida! " +
                "Buckets totais: %d, " +
                "Bucket '%s': %s, " +
                "URL: %s",
                bucketCount, bucketName, bucketStatus, minioUrl
            );
            
        } catch (Exception e) {
            return "✗ Erro ao conectar com MinIO: " + e.getMessage();
        }
    }
}