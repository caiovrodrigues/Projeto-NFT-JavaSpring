package com.apiNft.NFTAPI.services;

import com.apiNft.NFTAPI.Repositories.CommentRepository;
import com.apiNft.NFTAPI.dto.RequestCommentDTO;
import com.apiNft.NFTAPI.entidades.Comment;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    public List<Comment> findAll() {
        return repository.findAll();
    }
    @Transactional
    public Comment postComentario(Usuario usuario, Nft nft, RequestCommentDTO comentario){
        return repository.save(new Comment(usuario, nft, comentario.mensagem()));
    }

    @Transactional(readOnly = true)
    public Comment getComentario(Long id){
        return this.repository.findById(id).orElseThrow(() -> new RuntimeException("Comentário com o id " + id + " não encontrado."));
    }

    @Transactional
    public void delete(Long id){
        this.repository.deleteById(id);
    }
}
