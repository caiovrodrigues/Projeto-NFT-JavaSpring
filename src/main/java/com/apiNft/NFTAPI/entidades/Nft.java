package com.apiNft.NFTAPI.entidades;

import com.apiNft.NFTAPI.dto.RequestCadastroNft;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity(name = "nft")
@Table(name = "tb_nft")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String name;
    private String description;
    private Float price;
    private Integer qtd;
    private String img_url;

<<<<<<< HEAD
<<<<<<< HEAD
    @CreationTimestamp
    @Column(name = "data_criacao")
    private LocalDateTime date;

=======
>>>>>>> parent of 034e479 (Nome de classes alteradas)
=======
>>>>>>> parent of 034e479 (Nome de classes alteradas)
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Usuario user;

    @OneToMany(mappedBy = "nft", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comment;

    public Nft(RequestCadastroNft nft, Usuario usuario) {
        this.date = LocalDateTime.now();
        this.name = nft.name();
        this.description = nft.description();
        this.price = nft.price();
        this.qtd = nft.qtd();
        this.img_url = nft.img_url();
        this.user = usuario;
    }

}
