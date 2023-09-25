package com.apiNft.NFTAPI.controllers;

import com.apiNft.NFTAPI.Repositories.CommentRepository;
import com.apiNft.NFTAPI.Repositories.NftRepository;
import com.apiNft.NFTAPI.entidades.Comment;
import com.apiNft.NFTAPI.entidades.Nft;
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

    @GetMapping
    public List<Nft> findAll(){
        return nftRepository.findAll();
    }

    @GetMapping("/{id}")
    public Nft findById(@PathVariable Long id){
        return nftRepository.findById(id).get();
    }

    @PostMapping
    public String inserirNft(@RequestBody Nft nft){
        nftRepository.save(nft);
        return "Deu certo";
    }

    @PostMapping("/{id}")
    public String inserirComentario(@PathVariable Long id, @RequestBody List<Comment> comment){
        Nft nft = nftRepository.getReferenceById(id);
        nft.setComment((List<Comment>) comment);
        commentRepository.saveAll(comment);
        return "Deu certo";
    }

    @DeleteMapping
    public void delete(@RequestBody Nft nft){
        nftRepository.delete(nft);
    }
}
