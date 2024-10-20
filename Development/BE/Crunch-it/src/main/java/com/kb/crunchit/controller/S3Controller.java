package com.kb.crunchit.controller;

import com.kb.crunchit.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // MultipartFile을 임시 디렉토리에 파일로 저장
            File tempFile = File.createTempFile(file.getOriginalFilename(), null);
            file.transferTo(tempFile);

            // S3에 파일 업로드
            s3Service.uploadFile(file.getOriginalFilename(), tempFile);

            // 임시 파일 삭제
            tempFile.delete();

            return "File uploaded successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "File upload failed!";
        }
    }

@GetMapping("/download/{fileName}")
public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
    try (InputStream inputStream = s3Service.downloadFile(fileName)) {
        // S3에서 파일 다운로드 및 InputStream을 리소스로 사용
        ByteArrayResource resource = new ByteArrayResource(inputStream.readAllBytes());

        // 파일의 MIME 타입 추론
        String contentType = Files.probeContentType(Paths.get(fileName));
        if (contentType == null) {
            contentType = "application/octet-stream";  // MIME 타입을 추론할 수 없을 경우 기본값
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(null);  // 에러 발생 시 500 에러 반환
    }
}

    @DeleteMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName) {
        try {
            // S3에서 파일 삭제
            s3Service.deleteFile(fileName);
            return "File deleted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "File deletion failed!";
        }
    }
}