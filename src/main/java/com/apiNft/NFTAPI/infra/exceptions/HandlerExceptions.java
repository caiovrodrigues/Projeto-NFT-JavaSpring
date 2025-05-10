package com.apiNft.NFTAPI.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.InputMismatchException;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptions {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> notFoundEntity(EntityNotFoundException e, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatusCode.valueOf(404).value());
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity argumentosNaoValidos(
            MethodArgumentNotValidException e, BindingResult bindingResult, HttpServletRequest request) {
        System.out.println("Método do request: " + request.getMethod());
        List<FieldError> erros = bindingResult.getFieldErrors();
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request.getRequestURI(), e.getMessage(), 400, erros));
    }

    @ExceptionHandler(InputMismatchException.class)
    public ResponseEntity dadosNaoConferem(InputMismatchException ex, HttpServletRequest request) {
        return ResponseEntity.status(422)
                .body(new ErrorMessage(
                        request.getRequestURI(),
                        ex.getMessage(),
                        HttpStatusCode.valueOf(422).value()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity campoUnicoViolado(DataIntegrityViolationException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatusCode.valueOf(409))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request.getRequestURI(), "Já existe uma conta cadastrada com esse email ou username", 409));
    }
}
