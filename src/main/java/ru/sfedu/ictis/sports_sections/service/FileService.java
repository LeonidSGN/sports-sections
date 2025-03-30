package ru.sfedu.ictis.sports_sections.service;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile file) throws IOException;

    UrlResource getFile(String fileName);
}