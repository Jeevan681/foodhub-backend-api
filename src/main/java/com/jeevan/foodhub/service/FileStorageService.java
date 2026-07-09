package com.jeevan.foodhub.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(MultipartFile file);
}