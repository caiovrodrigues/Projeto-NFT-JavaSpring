package com.apiNft.NFTAPI.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    private String mensagem;

    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "nft_id")
    @JsonIgnore
    private Nft nft;

    public Comment(Usuario usuario, Nft nft, String mensagem){
        this.usuario = usuario;
        this.nft = nft;
        this.mensagem = mensagem;
    }
}
