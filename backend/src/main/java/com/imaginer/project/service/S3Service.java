package com.imaginer.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service
{

  private final S3Client s3Client;
  private final String bucketName;

  private final String region;

  public S3Service(@Value("${aws.access-key}") String accessKey,
                   @Value("${aws.secret-key}") String secretKey,
                   @Value("${aws.s3.region}") String region,
                   @Value("${aws.s3.bucket-name}") String bucketName)
  {

    this.region = region;

    this.s3Client = S3Client
      .builder()
      .region(Region.of(region))
      .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
      .build();
    this.bucketName = bucketName;
  }

  public String uploadFile(InputStream inputStream, String originalFilename, String contentType) {
    try {
      byte[] bytes = inputStream.readAllBytes();
      long contentLength = bytes.length;

      if (contentLength <= 0) {
        throw new IllegalArgumentException("File content is empty or invalid");
      }

      String uniqueFileName = UUID.randomUUID() + "-" + originalFilename;

      PutObjectRequest putObjectRequest = PutObjectRequest
        .builder()
        .bucket(bucketName)
        .key(uniqueFileName)
        .contentType(contentType)
        .build();


      s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(bytes));

      return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, uniqueFileName);

    } catch (IOException e) {
      throw new RuntimeException("Failed to read file content", e);
    }
  }

}
