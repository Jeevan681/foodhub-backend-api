package com.jeevan.foodhub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileUploadResponse {

    private String imageUrl;

    private String fileName;
}