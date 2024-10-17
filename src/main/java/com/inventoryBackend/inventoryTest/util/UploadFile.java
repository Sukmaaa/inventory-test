package com.inventoryBackend.inventoryTest.util;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadFile {
    private static final String UPLOAD_DIR = "uploads/";

    public String saveUploadedFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }

        // Ambil nama file asli dan ekstensinya
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";
        String baseFileName = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            baseFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        } else {
            baseFileName = originalFileName;
        }

        // Tambahkan timestamp di belakang nama file asli
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String newFileName = baseFileName + "_" + timeStamp + fileExtension;

        Path uploadPath = Paths.get(UPLOAD_DIR, newFileName);

        Files.createDirectories(uploadPath.getParent());
        Files.copy(file.getInputStream(), uploadPath);

        return uploadPath.toString();
    }
}
