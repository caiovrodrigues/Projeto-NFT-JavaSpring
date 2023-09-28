package com.apiNft.NFTAPI.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tb_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;

    private String usuario;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "nft_id")
    @JsonIgnore
    private Nft nft;

    public Comment(){}

    public Comment(Nft nft, String usuario, String comentario){
        this.date = LocalDateTime.now();
        this.nft = nft;
        this.usuario = usuario;
        this.comentario = comentario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Nft getNft() {
        return nft;
    }

    public void setNft(Nft nft) {
        this.nft = nft;
    }
}
