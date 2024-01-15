package com.apiNft.NFTAPI.entidades;

import com.apiNft.NFTAPI.dto.RequestCadastroUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "tb_usuarios")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Nft> nfts;

    public Usuario(RequestCadastroUsuario usuario) {
        this.email = usuario.email();
        this.username = usuario.username();
        this.password = usuario.senha();
    }
}
