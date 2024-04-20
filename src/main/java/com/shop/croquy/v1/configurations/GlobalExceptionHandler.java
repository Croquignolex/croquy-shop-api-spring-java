package com.shop.croquy.v1.configurations;

import com.shop.croquy.v1.dto.GenericResponse;
import com.shop.croquy.v1.helpers.GeneralHelper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<GenericResponse> handleUsernameNotFoundException() {
        return GeneralHelper.errorResponse(HttpStatus.BAD_REQUEST, USER_USERNAME_NOT_FOUND);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<GenericResponse> handleAuthenticationException() {
        return GeneralHelper.errorResponse(HttpStatus.NOT_ACCEPTABLE, INCORRECT_LOGIN_PASSWORD);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        return GeneralHelper.errorResponse(HttpStatus.BAD_REQUEST, errorMessages.toString());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<GenericResponse> handleConstraintViolationException(ConstraintViolationException ex){
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessages.add(violation.getMessage());
        }

        return GeneralHelper.errorResponse(HttpStatus.BAD_REQUEST, errorMessages.toString());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<GenericResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return GeneralHelper.errorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(value= MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GenericResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        return GeneralHelper.errorResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GenericResponse> handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        return GeneralHelper.errorResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<GenericResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return GeneralHelper.errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<GenericResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return GeneralHelper.errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<GenericResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return GeneralHelper.errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<GenericResponse> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex) {
        return GeneralHelper.errorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage());
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<GenericResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return GeneralHelper.errorResponse(HttpStatus.PAYLOAD_TOO_LARGE, FILE_SIZE_EXCEEDS);
    }

    @ExceptionHandler(value = SecurityException.class)
    public ResponseEntity<GenericResponse> handleSecurityException(SecurityException ex) {
        return GeneralHelper.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<GenericResponse> handleRuntimeException(RuntimeException ex) {
        return GeneralHelper.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<GenericResponse> handleCommonException(Exception ex) {
        return GeneralHelper.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}