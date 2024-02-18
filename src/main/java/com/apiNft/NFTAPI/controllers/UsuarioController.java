package com.apiNft.NFTAPI.controllers;

import com.apiNft.NFTAPI.dto.RequestCadastroUsuario;
import com.apiNft.NFTAPI.dto.RequestLoginUsuario;
import com.apiNft.NFTAPI.dto.ResponseCadastroUsuario;
import com.apiNft.NFTAPI.dto.ResponseLoginUsuario;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import com.apiNft.NFTAPI.services.UsuarioService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public Usuario getUser(@PathVariable Long id){
        Usuario user = usuarioService.getUser(id);
        return user;
    }

    @GetMapping("/nfts/{id}")
    public ResponseEntity<List<Nft>> getAllNftsFromUser(@PathVariable Long id){
        List<Nft> nfts = usuarioService.getAllNftsFromUser(id);
        return ResponseEntity.ok(nfts);
    }

    @PostMapping("/login")
    public ResponseEntity logarUser(@RequestBody @Valid RequestLoginUsuario dadosLogin){
        var token = usuarioService.logarUsuario(dadosLogin);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseCadastroUsuario> postUser(@RequestBody @Valid RequestCadastroUsuario usuario, UriComponentsBuilder uriBuilder){
        Usuario newUser = usuarioService.cadastraUsuario(usuario);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseCadastroUsuario(newUser));
    }

}
