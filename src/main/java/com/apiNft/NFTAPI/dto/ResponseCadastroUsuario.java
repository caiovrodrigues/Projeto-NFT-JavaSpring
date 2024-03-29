package com.apiNft.NFTAPI.dto;

import com.apiNft.NFTAPI.entidades.Usuario;

public record ResponseCadastroUsuario(Long id, String email, String username, String password) {
    public ResponseCadastroUsuario(Usuario newUser) {
        this(newUser.getId(), newUser.getEmail(), newUser.getUsername(), newUser.getPassword());
    }
}
