package com.swd.bike.core;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@Slf4j
public class BaseExceptionController {

    public BaseExceptionController() {
    }

    @ExceptionHandler({InternalException.class})
    public ResponseEntity<?> handleBusinessException(InternalException e) {
        log.error("Business error {}", e.getMessage());
        return ResponseEntity.ok(new ResponseBase<>(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("", e);
        return new ResponseEntity<>(new ResponseBase<>(1, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class
    })
    public ResponseEntity<?> handleArgumentInvalidException(BindException e) {
        Map<String, List<String>> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, List.of(Optional.ofNullable(errorMessage).orElse("")));
        });

        ResponseBase<?> responseBase = new ResponseBase<>(errors);
        responseBase.setCode(ResponseCode.INVALID_PARAM.getCode());
        responseBase.setMessage(ResponseCode.INVALID_PARAM.getMessage());

        return new ResponseEntity<>(responseBase, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex) {
        if (ex.getMessage().toLowerCase().contains("access is denied")) {
            return new ResponseEntity<>("Unauthorized Access", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleJsonErrors(HttpMessageNotReadableException exception) {
        Pattern ENUM_MSG = Pattern.compile("\\[[a-zA-Z,_ ]*\\]");
        if (exception.getCause() != null && exception.getCause() instanceof InvalidFormatException) {
            log.error("Invalid enum argument: {}", exception.getCause().getMessage());
            Matcher match = ENUM_MSG.matcher(exception.getMessage());
            if (match.find()) {
                return new ResponseEntity<>(new ResponseBase<>(1, "values should be: " + match.group(0)), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new ResponseBase<>(1, exception.getCause().getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ResponseBase<>(1, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
