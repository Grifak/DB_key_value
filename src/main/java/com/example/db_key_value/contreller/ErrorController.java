package com.example.db_key_value.contreller;

import com.example.db_key_value.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundEx(EntityNotFoundException e){
        ApiError apiError = ApiError.builder()
                .debugMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CsvException.class,
            FileEmptyException.class,
            DataBaseIsEmptyException.class,
            CsvIoException.class,
            InvalidFileFormatException.class,
            RequiredFieldIsEmptyException.class})
    public ResponseEntity<ApiError> handleBadRequestEx(RuntimeException e){
        ApiError apiError = ApiError.builder()
                .debugMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = ApiError.builder()
                .debugMessage(ex.getMessage())
                .build();

        return new ResponseEntity<>(apiError, status);
    }
}
