package com.inventoryBackend.inventoryTest.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class MimeTypeValidator {

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/jpg");

    public static boolean isValidMimeType(MultipartFile file) {
        return ALLOWED_MIME_TYPES.contains(file.getContentType());
    }
}