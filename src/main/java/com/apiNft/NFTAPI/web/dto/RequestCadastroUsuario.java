package com.apiNft.NFTAPI.web.dto;

import com.apiNft.NFTAPI.domain.entity.Usuario;
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

    public Usuario toEntity() {
        return Usuario.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
    }
}
