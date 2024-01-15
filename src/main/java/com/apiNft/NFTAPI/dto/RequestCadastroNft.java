package com.apiNft.NFTAPI.dto;

import com.apiNft.NFTAPI.entidades.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestCadastroNft(
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @NotBlank
        String description,
        @NotNull
        Float price,
        @NotNull
        Integer qtd,
        @NotBlank
        String img_url,
        @NotBlank
        Usuario usuario
        ){
}
