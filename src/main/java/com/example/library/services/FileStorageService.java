package com.example.library.services;

import com.example.library.models.BasicModel;
import com.example.library.models.FileEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${image.path}")
    private String imagePath;
    @Value("${upload.path}")
    private String filePath;

    public FileEntity storeFileToDisk(MultipartFile file, BasicModel basicModel) throws IOException {
        String uploadPath = filePath;
        if (!file.isEmpty()) {
            if(file.getContentType().startsWith("image/")) uploadPath = imagePath;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            String filename = basicModel.getId() + basicModel.getName() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));

            Path path = Paths.get(uploadPath).resolve(filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return new FileEntity(uploadPath, filename);
        }
        return null;
    }

    public void deleteFileFromDisk(FileEntity file) throws IOException {
        try {
            Path path = Paths.get(file.getPath(), file.getName());
            Files.deleteIfExists(path);
        } catch (NullPointerException e){}
    }

}
