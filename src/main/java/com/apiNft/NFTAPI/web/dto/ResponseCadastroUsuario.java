package com.apiNft.NFTAPI.web.dto;

import com.apiNft.NFTAPI.domain.entity.Usuario;

public record ResponseCadastroUsuario(Long id, String email, String username, String password) {
    public ResponseCadastroUsuario(Usuario newUser) {
        this(newUser.getId(), newUser.getEmail(), newUser.getUsername(), newUser.getPassword());
    }
}
