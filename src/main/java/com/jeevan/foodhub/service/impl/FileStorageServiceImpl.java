package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.config.UploadProperties;
import com.jeevan.foodhub.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageServiceImpl(
            UploadProperties uploadProperties) {

        this.fileStorageLocation =
                Paths.get(uploadProperties.getUploadDir())
                        .toAbsolutePath()
                        .normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Could not create upload directory.",
                    ex
            );
        }
    }

    @Override
    public String storeFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty.");
        }

        String originalFilename =
                StringUtils.cleanPath(file.getOriginalFilename());

        String extension = "";

        int index = originalFilename.lastIndexOf('.');

        if (index != -1) {
            extension = originalFilename.substring(index);
        }

        String fileName =
                UUID.randomUUID() + extension;

        try {

            Path targetLocation =
                    this.fileStorageLocation.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return "/uploads/" + fileName;

        } catch (IOException ex) {

            throw new RuntimeException(
                    "Could not store file.",
                    ex
            );
        }
    }
}