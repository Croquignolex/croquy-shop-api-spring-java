package com.shop.croquy.v1.dao.backoffice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.http.HttpStatusCode;

@Data
@Builder
@AllArgsConstructor
public class GenericResponse {
    private String message;
    private HttpStatusCode code;
}