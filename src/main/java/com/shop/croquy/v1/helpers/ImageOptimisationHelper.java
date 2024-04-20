package com.shop.croquy.v1.helpers;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;
import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.FILE_SYSTEM_ERROR;

@Component
@Slf4j
public class ImageOptimisationHelper {
    public static Map<String, String> saveFile(MultipartFile file, String folderPath, long maxiSize, List<String> acceptableExtensions) {
        String originalName = StringUtils.defaultString(file.getOriginalFilename());
        File folder = new File(folderPath);

        if (!folder.exists() && !folder.mkdirs()) throw new SecurityException(FILE_SYSTEM_ERROR);
        if (!StringUtils.isNotBlank(originalName) || originalName.contains("..")) throw new SecurityException(WRONG_ORIGINAL_FILE_NAME);

        try {
            if (file.getSize() > maxiSize) throw new MaxUploadSizeExceededException(file.getSize());
            if (!acceptableExtensions.contains(file.getContentType())) throw new IllegalArgumentException(WRONG_ORIGINAL_FILE_TYPE);

            String path = getUniquePath(folder);

            File serverFile = new File(folder.getAbsolutePath() + File.separator + path);

            file.transferTo(serverFile);

            Map<String, String> map = new HashMap<>();
            map.put("name", originalName);
            map.put("path", path);

            return map;
        }
        catch (IOException e) {
            log.error("################################# [Saving file error] ===> " + e.getMessage());

            throw new SecurityException(FILE_SYSTEM_ERROR);
        }
    }

    public static void deleteFile(String filePath, String folderPath) {
        File folder = new File(folderPath);
        File serverFile = new File(folder.getAbsolutePath() + File.separator + filePath);
        if(!serverFile.delete()) throw new SecurityException(FILE_SYSTEM_ERROR);
    }

    private static String getUniquePath(File folder) {
        String radomString = UUID.randomUUID().toString();

        File serverFile = new File(folder.getAbsolutePath() + File.separator + radomString);
        if(serverFile.exists()) return getUniquePath(folder);

        return radomString;
    }
}
