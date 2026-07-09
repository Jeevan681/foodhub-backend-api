package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.response.FileUploadResponse;
import com.jeevan.foodhub.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    public FileUploadController(
            FileStorageService fileStorageService) {

        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public ResponseEntity<FileUploadResponse> uploadImage(

            @RequestParam("file")
            MultipartFile file) {

        String imageUrl =
                fileStorageService.storeFile(file);

        String fileName =
                imageUrl.substring(
                        imageUrl.lastIndexOf("/") + 1
                );

        FileUploadResponse response =
                FileUploadResponse.builder()
                        .imageUrl(imageUrl)
                        .fileName(fileName)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}