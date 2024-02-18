package com.apiNft.NFTAPI.controllers;

import com.apiNft.NFTAPI.Repositories.NftRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroNft;
import com.apiNft.NFTAPI.dto.ResponseNftDTO;
import com.apiNft.NFTAPI.entidades.Comment;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import com.apiNft.NFTAPI.infra.security.JwtService;
import com.apiNft.NFTAPI.services.NftService;
import com.apiNft.NFTAPI.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nft")
public class NftController {

    private final NftRepository nftRepository;
    private final NftService nftService;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @GetMapping //Retorna todos os nfts
    public ResponseEntity<Page<Nft>> findAll(@PageableDefault(size = 9) Pageable pageable){
        Page<Nft> nfts = nftService.getAll(pageable);
        return ResponseEntity.ok(nfts);
    }

    @GetMapping("/{id}") //Retorna um nft
    public ResponseEntity<ResponseNftDTO> findById(@PathVariable Long id){
        Nft nft = nftService.getById(id);
        return ResponseEntity.ok(new ResponseNftDTO(nft));
    }

    @GetMapping("/{id}/comments") //Retorna todos os comentários de um nft
    public List<Comment> pegarComentarios(@PathVariable Long id){
        return nftRepository.getReferenceById(id).getComment();
    }

    @PostMapping
    public ResponseEntity<Nft> postNft(@RequestHeader(name = "Authorization") String token, @RequestBody @Valid RequestCadastroNft nft){
        Nft newNft = nftService.postNft(nft, token);
        URI uri = UriComponentsBuilder.fromPath("api/nft/{id}").buildAndExpand(newNft.getId()).toUri();
        return ResponseEntity.created(uri).body(newNft);
    }

    @PutMapping("/atualizar/{id}") //Atualiza um nft
    public Nft updateNft(@PathVariable Long id, @RequestBody Nft nft){
        return nftRepository.findById(id).map(campo -> {
            campo.setName(nft.getName());
            campo.setDescription(nft.getDescription());
            campo.setPrice(nft.getPrice());
            campo.setQtd(nft.getQtd());
            return nftRepository.save(campo);
        }).orElseGet(() -> nftRepository.save(nft));
    }

    @DeleteMapping("{id}") //Deleta um nft
    public void delete(@PathVariable Long id){
        nftService.delete(id);
    }

    @GetMapping("/{idNft}/canedit")
    public LogadoDTO verificaAuth(@RequestHeader(name = "Authorization") String token, @PathVariable Long idNft){
        String username = jwtService.validateToken(token.substring(7));
        Usuario usuario = usuarioService.findByUsername(username);

        Nft nft = this.nftService.getById(idNft);
        System.out.println("NFT: " + nft);
        if(nft.getUser().equals(usuario)){
            return new LogadoDTO(true);
        }
        return new LogadoDTO(false);
    }

    public record LogadoDTO(boolean logado){}
}
