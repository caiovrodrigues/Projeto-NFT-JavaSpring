package com.apiNft.NFTAPI.controllers;

import com.apiNft.NFTAPI.Repositories.CommentRepository;
import com.apiNft.NFTAPI.Repositories.NftRepository;
import com.apiNft.NFTAPI.dto.DadosCadastroNft;
import com.apiNft.NFTAPI.entidades.Comment;
import com.apiNft.NFTAPI.entidades.Nft;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:4200")
@RestController
@RequestMapping("/nft")
public class NftController {

    @Autowired
    private NftRepository nftRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping //Retorna todos os nfts
    public List<Nft> findAll(){
        return nftRepository.findAll();
    }

    @GetMapping("/{id}") //Retorna um nft
    public Nft findById(@PathVariable Long id){
        return nftRepository.findById(id).get();
    }

    @GetMapping("comments/{id}") //Retorna todos os comentários de um nft
    public List<Comment> pegarComentarios(@PathVariable Long id){
        return nftRepository.getReferenceById(id).getComment();
    }

    @PostMapping //Cria um nft
    public void inserirNft(@RequestBody @Valid DadosCadastroNft nft){
        nftRepository.save(new Nft(nft));
    }

    @PostMapping("/{id}") //Cria um comentário em um nft
    public void inserirComentario(@PathVariable Long id, @RequestBody Comment comment){
        Nft nft = nftRepository.getReferenceById(id);

        var comentario = new Comment(nft, comment.getUsuario(), comment.getComentario());

        commentRepository.save(comentario);
    }

    @PutMapping("/atualizar/{id}") //Atualiza um nft
    public Nft replaceNft(@PathVariable Long id, @RequestBody Nft nft){
        return nftRepository.findById(id).map(campo -> {
            campo.setName(nft.getName());
            campo.setDescription(nft.getDescription());
            campo.setPrice(nft.getPrice());
            campo.setQtd(nft.getQtd());
            return nftRepository.save(campo);
        }).orElseGet(() -> {
            return nftRepository.save(nft);
        });
    }

    @DeleteMapping //Deleta um nft
    public void delete(@RequestBody Nft nft){
        nftRepository.delete(nft);
    }
}
