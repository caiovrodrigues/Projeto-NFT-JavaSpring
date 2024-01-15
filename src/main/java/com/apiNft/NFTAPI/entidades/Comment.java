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
    private LocalDateTime date;

    private String usuario;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "nft_id")
    @JsonIgnore
    private Nft nft;

    public Comment(Nft nft, String usuario, String comentario){
        this.date = LocalDateTime.now();
        this.nft = nft;
        this.usuario = usuario;
        this.comentario = comentario;
    }
}
