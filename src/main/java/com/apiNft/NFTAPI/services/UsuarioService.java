package com.apiNft.NFTAPI.services;

import com.apiNft.NFTAPI.Repositories.UsuarioRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroUsuario;
import com.apiNft.NFTAPI.dto.RequestLoginUsuario;
import com.apiNft.NFTAPI.entidades.AppRole;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import com.apiNft.NFTAPI.infra.security.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Usuario getUser(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com o id: " + id + " não encontrado."));
    }

    public List<Nft> getAllNftsFromUser(Long id) {
        Usuario usuario = this.getUser(id);
        return usuario.getNfts();
    }

    @Transactional
    public Usuario cadastraUsuario(RequestCadastroUsuario usuario) {
        if(!usuario.getPassword().equals(usuario.getConfirmPassword())){
            throw new InputMismatchException("Senha e confirma senha não conferem");
        }
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        return usuarioRepository.save(new Usuario(usuario));
    }

    @Transactional(readOnly = true)
    public String logarUsuario(RequestLoginUsuario dados) {
        Usuario user = usuarioRepository.findByUsername(dados.username())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User '%s' not found.", dados.username())));

        if(Objects.isNull(user)){
            throw new InputMismatchException("O usuário não existe");
        }

        if(!new BCryptPasswordEncoder().matches(dados.password(), user.getPassword())){
            throw new InputMismatchException("A senha está incorreta");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(dados.username(), dados.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        return jwtService.generateToken((Usuario) auth.getPrincipal());
    }

    public Usuario findByUsername(String username){
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário " + username + " não existe"));
    }

    public String findOrSaveByToken(String token) {
        DecodedJWT decoded = JWT.decode(token);
        String email = decoded.getClaim("email").asString();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        Usuario usuario = usuarioOpt.orElseGet(() -> {
            Usuario userSaved = Usuario.builder().email(email).role(AppRole.USER).build();
            return usuarioRepository.save(userSaved);
        });

        return jwtService.generateToken(usuario);
    }
}
