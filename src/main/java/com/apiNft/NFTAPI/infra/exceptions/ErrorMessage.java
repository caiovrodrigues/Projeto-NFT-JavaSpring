package com.apiNft.NFTAPI.infra.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErrorMessage {

    public String path;
    public int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Map<String, String>> erros = new ArrayList<>();
    public LocalDateTime timestamp;

    public ErrorMessage(String path, String message, int statusCode, List<FieldError> errors){
        this.path = path;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();

        var list = errors.stream().map((erro) -> Map.of(erro.getField(), erro.getDefaultMessage())).toList();

        this.erros = list;
    }

    public ErrorMessage(String path, String message, int statusCode){
        this.path = path;
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();
    }

}
