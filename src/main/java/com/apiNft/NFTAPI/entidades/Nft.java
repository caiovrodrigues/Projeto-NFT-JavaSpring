package com.apiNft.NFTAPI.entidades;

import com.apiNft.NFTAPI.dto.DadosCadastroNft;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "nft")
@Table(name = "tb_nft")
@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "nft", cascade = CascadeType.ALL)
    private List<Comment> comment;

    public Nft(DadosCadastroNft nft) {
        this.date = LocalDateTime.now();
        this.name = nft.name();
        this.description = nft.description();
        this.price = nft.price();
        this.qtd = nft.qtd();
        this.img_url = nft.img_url();
    }
}
