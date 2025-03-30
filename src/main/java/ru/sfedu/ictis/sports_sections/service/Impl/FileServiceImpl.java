package ru.sfedu.ictis.sports_sections.service.Impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.service.FileService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload.base-url}")
    private String baseUrl;

    @Value("${resources.directory}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, uniqueFileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return baseUrl + uniqueFileName;
    }

    public UrlResource getFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new CustomException(ErrorCodes.EXCEPTION_HANDLER_NOT_PROVIDED);
            }
        } catch (MalformedURLException e) {
            throw new CustomException(ErrorCodes.UNKNOWN);
        }
    }
}