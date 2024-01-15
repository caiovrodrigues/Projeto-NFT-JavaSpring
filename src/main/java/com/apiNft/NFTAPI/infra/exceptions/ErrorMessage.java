package com.apiNft.NFTAPI.infra.exceptions;


import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErrorMessage(String path, String message, int codeStatus, LocalDateTime timestamp) {
    public ErrorMessage(String path, String message, int codeStatus){
        this(path, message, codeStatus, LocalDateTime.now());
    }
}
