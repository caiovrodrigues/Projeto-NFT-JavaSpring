package com.apiNft.NFTAPI.controllers;

import com.apiNft.NFTAPI.Repositories.CommentRepository;
import com.apiNft.NFTAPI.Repositories.NftRepository;
import com.apiNft.NFTAPI.entidades.Comment;
import com.apiNft.NFTAPI.entidades.Nft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NftRepository nftRepository;

    @GetMapping
    public List<Comment> findALl(){
        return commentRepository.findAll();
    }

    @PostMapping()
    public String inserirComentario(@RequestBody Comment comment){
        commentRepository.save(comment);
        return "Deu certo";
    }
}
