package com.shop.croquy.v1.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IStorageService {
    Boolean saveFileToFileSystem(MultipartFile file) throws IOException;

    byte[] downloadFileFromFileSystem(String fileName) throws IOException;
}
