package com.apiNft.NFTAPI.Repositories;

import com.apiNft.NFTAPI.entidades.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);
}
