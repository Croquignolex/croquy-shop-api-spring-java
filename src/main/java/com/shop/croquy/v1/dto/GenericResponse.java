package com.shop.croquy.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GenericResponse {
    private int statusCode;
    private String message;
}