package com.apiNft.NFTAPI.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCadastroUsuario {
    @NotBlank
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "username não deve estar em branco")
    private String username;

    @NotBlank(message = "senha não deve estar em branco")
    private String password;

    @NotBlank(message = "confirma senha não deve estar em branco")
    private String confirmPassword;
}
