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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "tb_nft")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Float price;

    private Integer qtd;

    private String urlMinio;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime update_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Usuario user;

    @OneToMany(mappedBy = "nft", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comment;

    public Nft(RequestCadastroNft nft, Usuario usuario) {
        this.name = nft.name();
        this.description = nft.description();
        this.price = nft.price();
        this.qtd = nft.qtd();
        this.user = usuario;
    }

}
