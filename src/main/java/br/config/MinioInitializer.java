// src/main/java/br/config/MinioInitializer.java
package br.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.GetBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;

import java.util.List;

@ApplicationScoped
public class MinioInitializer {

    @Inject
    S3Client s3Client;

    @ConfigProperty(name = "minio.bucket")
    String bucketName;

    @PostConstruct
    public void init() {
        try {
            System.out.println("🚀 Inicializando MinIO...");
            
            List<Bucket> buckets = s3Client.listBuckets().buckets();
            boolean bucketExists = buckets.stream()
                .anyMatch(b -> b.name().equals(bucketName));
            
            if (!bucketExists) {
                System.out.println("📦 Criando bucket: " + bucketName);
                s3Client.createBucket(CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build());
            }
            
            System.out.println("🔓 Configurando bucket como público...");
            String policy = """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Effect": "Allow",
                            "Principal": "*",
                            "Action": ["s3:GetObject"],
                            "Resource": ["arn:aws:s3:::%s/*"]
                        }
                    ]
                }
                """.formatted(bucketName);
            
            s3Client.putBucketPolicy(PutBucketPolicyRequest.builder()
                .bucket(bucketName)
                .policy(policy)
                .build());
            
            System.out.println("✅ MinIO inicializado com sucesso!");
            
        } catch (Exception e) {
            System.err.println("⚠️  Erro na inicialização do MinIO: " + e.getMessage());
        }
    }
}