package com.apiNft.NFTAPI.services;

import com.apiNft.NFTAPI.domain.repositories.CommentRepository;
import com.apiNft.NFTAPI.web.dto.RequestCommentDTO;
import com.apiNft.NFTAPI.domain.entity.Comment;
import com.apiNft.NFTAPI.domain.entity.Nft;
import com.apiNft.NFTAPI.domain.entity.Usuario;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository repository;

    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Comment postComentario(Usuario usuario, Nft nft, RequestCommentDTO comentario) {
        return repository.save(new Comment(usuario, nft, comentario.mensagem()));
    }

    @Transactional(readOnly = true)
    public Comment getComentario(Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário com o id " + id + " não encontrado."));
    }

    @Transactional
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
