package com.apiNft.NFTAPI.web.controllers;

import com.apiNft.NFTAPI.web.dto.RequestCadastroUsuario;
import com.apiNft.NFTAPI.web.dto.RequestLoginUsuario;
import com.apiNft.NFTAPI.web.dto.ResponseCadastroUsuario;
import com.apiNft.NFTAPI.domain.entity.Nft;
import com.apiNft.NFTAPI.domain.entity.Usuario;
import com.apiNft.NFTAPI.infra.security.RoleAdministrador;
import com.apiNft.NFTAPI.services.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PreAuthorize(value = "#username == authentication.name || hasAuthority('ADMIN')")
    @GetMapping("/{username}")
    public Usuario getUser(@PathVariable String username) {
        return usuarioService.getUser(username);
    }

    @RoleAdministrador
    @GetMapping("/{username}/nfts")
    public ResponseEntity<List<Nft>> getAllNftsFromUser(@PathVariable String username) {
        List<Nft> nfts = usuarioService.getAllNftsFromUser(username);
        return ResponseEntity.ok(nfts);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseCadastroUsuario> postUser(
            @RequestBody @Valid RequestCadastroUsuario usuario, UriComponentsBuilder uriBuilder) {
        Usuario newUser = usuarioService.cadastraUsuario(usuario);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseCadastroUsuario(newUser));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> logarUser(@RequestBody @Valid RequestLoginUsuario dadosLogin) {
        var token = usuarioService.logarUsuario(dadosLogin);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login/oauth")
    public void loginUserByGoogle(@RequestBody String token, HttpServletResponse response) throws IOException {
        int indexOfFinalToken = token.lastIndexOf("&");
        String jwtTokenOAuth = token.substring("credential=".length(), indexOfFinalToken);
        var tokenJWT = usuarioService.findOrSaveByToken(jwtTokenOAuth);
        System.out.println(jwtTokenOAuth);
        response.sendRedirect("http://localhost:4201?" + "token=" + tokenJWT);
    }
}
