package com.apiNft.NFTAPI.services;

import com.apiNft.NFTAPI.Repositories.UsuarioRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroUsuario;
import com.apiNft.NFTAPI.dto.RequestLoginUsuario;
import com.apiNft.NFTAPI.entidades.AppRole;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import com.apiNft.NFTAPI.infra.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Usuario getUser(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com o id: " + id + " não encontrado."));
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
        return repository.save(new Usuario(usuario));
    }

    @Transactional(readOnly = true)
    public String logarUsuario(RequestLoginUsuario dados) {
        Usuario user = repository.findByUsername(dados.username())
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

    @Transactional
    public Usuario findByUsername(String username){
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário " + username + " não existe"));
    }
}
