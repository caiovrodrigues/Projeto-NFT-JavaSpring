package com.apiNft.NFTAPI.controllers;

import com.apiNft.NFTAPI.Repositories.CommentRepository;
import com.apiNft.NFTAPI.Repositories.NftRepository;
import com.apiNft.NFTAPI.dto.RequestCommentDTO;
import com.apiNft.NFTAPI.entidades.Comment;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import com.apiNft.NFTAPI.services.CommentService;
import com.apiNft.NFTAPI.services.NftService;
import com.apiNft.NFTAPI.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    private final UsuarioService usuarioService;

    private final NftService nftService;


    @GetMapping //Retorna todos os comentários
    public List<Comment> findALl(){
        return commentService.findAll();
    }

    @PostMapping("/usuario/{userId}/nft/{nftId}") //Salva um comentário (Não está sendo utilizado)
    public ResponseEntity<Comment> postComentario(@PathVariable Long userId, @PathVariable Long nftId, @RequestBody RequestCommentDTO comentario){
        Usuario usuario = usuarioService.getUser(userId);
        Nft nft = nftService.getById(nftId);
        Comment newComment = commentService.postComentario(usuario, nft, comentario);
        return ResponseEntity.ok(newComment);
    }
}
