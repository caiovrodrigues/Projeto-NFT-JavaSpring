package com.apiNft.NFTAPI.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tb_comment")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
<<<<<<< HEAD
<<<<<<< HEAD
    private String mensagem;

    @CreationTimestamp
    @Column(name = "data_criacao")
=======
>>>>>>> parent of 034e479 (Nome de classes alteradas)
=======
>>>>>>> parent of 034e479 (Nome de classes alteradas)
    private LocalDateTime date;
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "nft_id")
    @JsonIgnore
    private Nft nft;

    public Comment(Usuario usuario, Nft nft, String mensagem){
        this.date = LocalDateTime.now();
        this.usuario = usuario;
        this.nft = nft;
        this.mensagem = mensagem;
    }
}
