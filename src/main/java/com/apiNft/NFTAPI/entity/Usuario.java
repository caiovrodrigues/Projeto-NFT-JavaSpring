package com.apiNft.NFTAPI.entity;

import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Builder
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_usuarios")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Nft> nfts;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
