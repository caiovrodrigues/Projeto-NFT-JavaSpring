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
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NftRepository nftRepository;

    @GetMapping //Retorna todos os comentários
    public List<Comment> findALl(){
        return commentRepository.findAll();
    }

    @PostMapping("/enviar/{id}") //Salva um comentário (Não está sendo utilizado)
    public String inserir(@PathVariable Long id, @RequestBody Comment comentario){
        commentRepository.save(comentario);
        return "Recebemos sua tentativa de adicionar um comentário";
    }
}
