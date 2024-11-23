package ru.erdc.acts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
@Service
public class FileStorageService {
    @Autowired
    private ResourceLoader resourceLoader;
   private final String dir = "signedacts/";
    private final String uploadDir = "src/main/resources/static/signedacts";

    public String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        String str = filePath.toString();
        System.out.println(str);
//        String filePathToDB = str.substring(8, 14);
        String filePathToDB = dir+fileName;
        System.out.println(filePathToDB);
        System.out.println(filePath.toString());
//        return filePathToDB;
        return filePathToDB.toString();
    }
}
