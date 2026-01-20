package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.exceptions.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class S3Service {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    String uploadAndGetAddress(MultipartFile file) {
        if (file == null) {
            return "";
        }

        try {
            return uploadFile(file);
        } catch (Exception e) {
            throw new GlobalExceptionHandler.ImageUploadException(e.getMessage());
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();


        long contentLength = file.getSize();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .contentLength(contentLength)
                .build();

        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), contentLength));

        return getFileUrl(key);
    }

    public String getFileUrl(String key) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }
}
