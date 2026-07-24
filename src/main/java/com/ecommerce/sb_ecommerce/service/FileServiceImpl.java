package com.ecommerce.sb_ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        //Renaming to new filename jus to avoid overriding existing files
        String randomId = UUID.randomUUID().toString();
        // original name = test.png --> randomId is 1234---> 1234.png
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Path filePath = Paths.get(path, fileName);
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

}
