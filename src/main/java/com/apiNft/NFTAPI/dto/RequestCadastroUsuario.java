package com.apiNft.NFTAPI.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestCadastroUsuario(
        @NotBlank
        @Email(message = "O email deve ser válido")
        @Column(unique = true)
        String email,
        @NotBlank
        String username,
        String senha,
        String confirmaSenha) {
}
