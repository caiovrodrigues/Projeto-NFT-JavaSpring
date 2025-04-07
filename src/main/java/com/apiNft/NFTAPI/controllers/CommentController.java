package com.apiNft.NFTAPI.controllers;

import com.apiNft.NFTAPI.dto.RequestCommentDTO;
import com.apiNft.NFTAPI.dto.ResponseComentarioNftDTO;
import com.apiNft.NFTAPI.entidades.Comment;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import com.apiNft.NFTAPI.infra.security.JwtService;
import com.apiNft.NFTAPI.services.CommentService;
import com.apiNft.NFTAPI.services.NftService;
import com.apiNft.NFTAPI.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final UsuarioService usuarioService;
    private final NftService nftService;
    private final JwtService jwtService;

    @GetMapping //Retorna todos os comentários
    public List<Comment> findALl(){
        return commentService.findAll();
    }

    @GetMapping("nft/{id}") //Retorna comentários de um nft
    public ResponseEntity<List<ResponseComentarioNftDTO>> findAllNfts(@PathVariable Long id){
        Nft nft = nftService.getById(id);
        List<Comment> commentsList = nft.getComment();
        List<ResponseComentarioNftDTO> response = commentsList.stream().map((comment -> new ResponseComentarioNftDTO(comment.getId() ,comment.getUsuario().getId(), comment.getUsuario().getUsername(), comment.getMensagem(), "https://img.com", comment.getDate()))).toList();
        return ResponseEntity.ok(response);
    }

    //Postar comentário
    @PostMapping("/nft/{nftId}")
    public ResponseEntity<ResponseComentarioNftDTO> postComentario(
            @RequestHeader(name = "Authorization") String token,
            @PathVariable Long nftId,
            @RequestBody RequestCommentDTO comentario
    ){
        String username = jwtService.validateToken(token.substring(7));
        Usuario usuario = usuarioService.findByUsername(username);
        Nft nft = nftService.getById(nftId);

        Comment newComment = commentService.postComentario(usuario, nft, comentario);
        ResponseComentarioNftDTO response = new ResponseComentarioNftDTO(
                newComment.getId(),
                usuario.getId(),
                usuario.getUsername(),
                comentario.mensagem(),
                "https://img.url",
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}/user/{userId}")
    public void deletarComentario(@PathVariable Long id, @PathVariable Long userId){
        Comment comentario = this.commentService.getComentario(id);
        if(!comentario.getUsuario().getId().equals(userId)){
            throw new InputMismatchException("O usuário não tem permissão de deletar esse comentário");
        }
        this.commentService.delete(id);
    }
}
