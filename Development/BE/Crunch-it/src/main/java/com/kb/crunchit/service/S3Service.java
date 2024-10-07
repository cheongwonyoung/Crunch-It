package com.kb.crunchit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:properties/s3.properties")
public class S3Service {

    private final S3Client s3Client;
    @Value("${aws_bucket_name}")
    private String bucketName;  // S3 버킷 이름
    @Value("${aws_region}")
    private String region;


    // 파일 업로드 메서드
    public String uploadFile(String keyName, File file) {
        System.out.println("buckName = " + bucketName);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
        System.out.println(response.toString());

        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + keyName;
    }

    // 파일 다운로드 메서드
    public InputStream downloadFile(String keyName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        return s3Client.getObject(getObjectRequest);
    }

    // 파일 삭제 메서드
    public void deleteFile(String keyName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}
