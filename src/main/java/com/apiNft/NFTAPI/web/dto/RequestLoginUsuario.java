package com.apiNft.NFTAPI.web.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestLoginUsuario(@NotBlank String username, @NotBlank String password) {}
