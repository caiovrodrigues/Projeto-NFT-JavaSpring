package com.apiNft.NFTAPI.web.dto;

import java.time.LocalDateTime;

public record ResponseComentarioNftDTO(
        Long id, Long userId, String username, String message, String userImgUrl, LocalDateTime date) {}
