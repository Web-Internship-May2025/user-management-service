package com.example.user_management_service.service;

import com.example.user_management_service.aspect.Monitored;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Monitored
public class FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String saveImage(MultipartFile imageFile) {
        try {
            Path uploadPath = Paths.get("../user-managment-service/"+uploadDir+"/country").toAbsolutePath().normalize();
            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFilename = imageFile.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            }

            String timestamp = String.valueOf(System.currentTimeMillis());
            String newFileName = String.format("country_icon_%s%s", timestamp, extension);
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file. Error: " + ex.getMessage(), ex);
        }
    }

    public void deleteImage(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            logger.warn("deleteImage called with empty or null filename");
            return;
        }

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                logger.info("Successfully deleted file: {}", fileName);
            } else {
                logger.warn("File not found, cannot delete: {}", fileName);
            }
        } catch (IOException ex) {
            logger.error("Error deleting file: {}", fileName, ex);
        }
    }
}


