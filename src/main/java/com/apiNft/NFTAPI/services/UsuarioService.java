package com.apiNft.NFTAPI.services;

import com.apiNft.NFTAPI.Repositories.UsuarioRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroUsuario;
import com.apiNft.NFTAPI.dto.RequestLoginUsuario;
import com.apiNft.NFTAPI.entity.Nft;
import com.apiNft.NFTAPI.entity.Role;
import com.apiNft.NFTAPI.entity.Usuario;
import com.apiNft.NFTAPI.infra.security.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.persistence.EntityNotFoundException;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Usuario getUser(Long id) {
        return usuarioRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com o id: " + id + " não encontrado."));
    }

    public List<Nft> getAllNftsFromUser(Long id) {
        Usuario usuario = this.getUser(id);
        return usuario.getNfts();
    }

    @Transactional
    public Usuario cadastraUsuario(RequestCadastroUsuario usuarioInput) {
        if (!usuarioInput.getPassword().equals(usuarioInput.getConfirmPassword())) {
            throw new InputMismatchException("Senha e confirma senha não conferem");
        }
        var newUsuario = Usuario.builder()
                .email(usuarioInput.getEmail())
                .username(usuarioInput.getUsername())
                .password(passwordEncoder.encode(usuarioInput.getPassword()))
                .build();
        return usuarioRepository.save(newUsuario);
    }

    @Transactional(readOnly = true)
    public String logarUsuario(RequestLoginUsuario loginInput) {
        Usuario user = findByUsername(loginInput.username());

        if (Objects.isNull(user)) {
            throw new InputMismatchException("O usuário não existe");
        }

        if (!new BCryptPasswordEncoder().matches(loginInput.password(), user.getPassword())) {
            throw new InputMismatchException("A senha está incorreta");
        }

        return jwtService.generateToken(user);
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário " + username + " não existe"));
    }

    public String findOrSaveByToken(String token) {
        DecodedJWT decoded = JWT.decode(token);
        String email = decoded.getClaim("email").asString();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        Usuario usuario = usuarioOpt.orElseGet(() -> {
            Usuario userSaved = Usuario.builder().email(email).role(Role.USER).build();
            return usuarioRepository.save(userSaved);
        });

        return jwtService.generateToken(usuario);
    }
}
