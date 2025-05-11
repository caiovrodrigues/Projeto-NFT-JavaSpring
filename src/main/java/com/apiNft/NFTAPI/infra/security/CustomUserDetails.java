package com.apiNft.NFTAPI.infra.security;

import com.apiNft.NFTAPI.entity.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record CustomUserDetails(Usuario principal) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        if ("ADMIN".equals(principal.getRole().name())) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        if ("SYSADMIN".equals(principal.getRole().name())) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            authorities.add(new SimpleGrantedAuthority("SYSADMIN"));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return principal.getPassword();
    }

    @Override
    public String getUsername() {
        return principal.getUsername();
    }

    public String getEmail() {
        return principal.getEmail();
    }
}
