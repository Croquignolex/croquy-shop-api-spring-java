package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.MediaType;
import com.shop.croquy.v1.entities.Media;
import com.shop.croquy.v1.repositories.MediaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageService {
    private final MediaRepository mediaRepository;

    @Value("${media.saving.directory}")
    private String basePath;

    public Boolean saveFileToFileSystem(MultipartFile file) throws IOException {
        String filePath = basePath + file.getOriginalFilename();

        Media media = new Media();

        media.setName(file.getOriginalFilename());
//        media.setType(file.getContentType());
        media.setType(MediaType.IMAGE);
        media.setPath(filePath);

        try {
            mediaRepository.save(media);
            file.transferTo(new File(filePath));

            return true;
        } catch (Exception e) {
            log.info("File upload error ===> " + e.getMessage());
        }

        return false;
    }

    public byte[] downloadFileFromFileSystem(String mediaName) throws IOException {
        var media = mediaRepository.findByName(mediaName).orElseThrow(() -> new FileNotFoundException("Invalid media name"));
        String filePath = media.getPath();

        return Files.readAllBytes(new File(filePath).toPath());
    }
}
