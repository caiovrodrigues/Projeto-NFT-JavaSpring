package com.apiNft.NFTAPI.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestCadastroUsuario(
        @NotBlank
        @Email(message = "O email deve ser válido")
        String email,
        @NotBlank(message = "username não deve estar em branco")
        String username,
        @NotBlank(message = "senha não deve estar em branco")
        String senha,
        @NotBlank(message = "confirma senha não deve estar em branco")
        String confirmaSenha) {
}
