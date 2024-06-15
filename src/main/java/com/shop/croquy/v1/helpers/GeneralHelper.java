package com.shop.croquy.v1.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.croquy.v1.dto.GenericResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

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

    public static Optional<Integer> stringToInt(String str) {
        Optional<Integer> opt = Optional.empty();
        try {
            opt = Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            log.error("################################# [String to Integer error] ===> " + e.getMessage());
        }
        return opt;
    }

    public static Optional<Date> textToDate(String str) {
        Optional<Date> opt = Optional.empty();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            opt = Optional.of(formatter.parse(str));
        } catch (ParseException e) {
            log.error("################################# [String to Date error] ===> " + e.getMessage());
        }
        return opt;
    }

    public static Pageable buildPageable(int pageNumber, int pageSize, String sort, String dir) {
        Sort.Direction direction = Objects.equals(dir, "desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(direction, sort)
        );
    }
}
