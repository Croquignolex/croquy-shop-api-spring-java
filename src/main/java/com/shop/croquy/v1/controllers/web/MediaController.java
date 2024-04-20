package com.shop.croquy.v1.controllers.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.FILE_SYSTEM_ERROR;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/media/{path}")
public class MediaController {
    @Value("${media.saving.directory}")
    private String mediaFolderPath;

    @GetMapping
    public void index(
            @PathVariable String path,
            @RequestParam(defaultValue = IMAGE_PNG_VALUE) String type,
            HttpServletResponse response
    ) {
        File folder = new File(mediaFolderPath);

        try {
            FileInputStream fileInputStream = new FileInputStream(folder.getAbsolutePath() + File.separator + path);
            response.setContentType(type);
            StreamUtils.copy(fileInputStream, response.getOutputStream());
            fileInputStream.close();
        } catch (IOException e) {
            log.error("################################# [Download file error] ===> " + e.getMessage());

            throw new RuntimeException(FILE_SYSTEM_ERROR);
        }
    }
}
