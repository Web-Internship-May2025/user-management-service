package com.example.user_management_service.controller;

import com.example.user_management_service.aspect.Monitored;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
@Monitored
public class ImageController {
    @Value("${app.upload.dir}")
    private String uploadDir;
    @GetMapping("/{folder}/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename, @PathVariable String folder) {
        try {
            Path uploadPath = Paths.get("../user-managment-service/"+uploadDir+"/"+folder).toAbsolutePath().normalize();
            Path imagePath = uploadPath.resolve(filename);
            if (!Files.exists(imagePath) || !Files.isRegularFile(imagePath)) {
                return ResponseEntity.notFound().build();
            }
            Resource resource = new UrlResource(imagePath.toUri());


            String contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
