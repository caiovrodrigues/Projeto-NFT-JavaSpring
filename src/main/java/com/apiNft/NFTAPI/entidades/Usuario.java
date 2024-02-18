package com.apiNft.NFTAPI.entidades;

import com.apiNft.NFTAPI.dto.RequestCadastroUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Table(name = "tb_usuarios")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private AppRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Nft> nfts;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Usuario(RequestCadastroUsuario usuario) {
        this.email = usuario.getEmail();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.role = AppRole.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        if(role.name().equals("SYSADMIN")){
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            authorities.add(new SimpleGrantedAuthority("SYSADMIN"));
        }
        if(role.name().equals("ADMIN")){
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
