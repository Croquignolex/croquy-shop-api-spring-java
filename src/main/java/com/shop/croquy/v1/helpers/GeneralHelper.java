package com.shop.croquy.v1.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.croquy.v1.dto.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class GeneralHelper {
    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static ResponseEntity<GenericResponse> errorResponse(HttpStatus httpStatus, String message) {
        GenericResponse rep = new GenericResponse(httpStatus.value(), message);

        log.error("################################# [" + rep.getStatusCode() + "] ===> " + rep.getMessage());

        return ResponseEntity.status(rep.getStatusCode()).body(rep);
    }
}