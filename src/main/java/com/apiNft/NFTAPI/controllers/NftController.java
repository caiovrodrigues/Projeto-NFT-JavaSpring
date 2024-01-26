package com.apiNft.NFTAPI.controllers;

import com.apiNft.NFTAPI.Repositories.CommentRepository;
import com.apiNft.NFTAPI.Repositories.NftRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroNft;
import com.apiNft.NFTAPI.dto.ResponseNftDTO;
import com.apiNft.NFTAPI.entidades.Comment;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.services.NftService;
import com.apiNft.NFTAPI.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nft")
public class NftController {

    private final NftRepository nftRepository;

    private final NftService nftService;

    @GetMapping //Retorna todos os nfts
    public ResponseEntity<List<Nft>> findAll(){
        List<Nft> nfts = nftService.getAll();
        return ResponseEntity.ok(nfts);
    }

    @GetMapping("/{id}") //Retorna um nft
    public ResponseEntity<ResponseNftDTO> findById(@PathVariable Long id){
        Nft nft = nftService.getById(id);
        return ResponseEntity.ok(new ResponseNftDTO(nft));
    }

    @GetMapping("comments/{id}") //Retorna todos os comentários de um nft
    public List<Comment> pegarComentarios(@PathVariable Long id){
        return nftRepository.getReferenceById(id).getComment();
    }

    @Transactional
    @PostMapping("/usuario/{id}")
    public ResponseEntity<Nft> postNft(@PathVariable Long id, @RequestBody RequestCadastroNft nft){
        Nft newNft = nftService.postNft(id, nft);
        return ResponseEntity.ok(newNft);
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

    @GetMapping("/canedit/nft/{idNft}/usuario/{idUser}")
    public LogadoDTO verificaAuth(@PathVariable Long idNft, @PathVariable Long idUser){
        Nft nft = this.nftService.getById(idNft);
        if(nft.getUser().getId().equals(idUser)){
            return new LogadoDTO(true);
        }
        return new LogadoDTO(false);
    }

    public record LogadoDTO(boolean logado){}
}
