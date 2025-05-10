package com.apiNft.NFTAPI;

import static org.assertj.core.api.Assertions.assertThat;

import com.apiNft.NFTAPI.Repositories.UsuarioRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroUsuario;
import com.apiNft.NFTAPI.entidades.Usuario;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTests {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Deve pegar um usuário com sucesso no BD")
    void findByUsernameCase1() {
        String username = "biscas";
        createUsuario(new RequestCadastroUsuario("caio@email.com", username, "123456", "123456"));
        Optional<Usuario> byUsername = usuarioRepository.findByUsername(username);

        assertThat(byUsername.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Não deve pegar nenhum usuário no BD quando o usuário não existe")
    void findByUsernameCase2() {
        String username = "biscas";
        Optional<Usuario> byUsername = usuarioRepository.findByUsername(username);

        assertThat(byUsername.isEmpty()).isTrue();
    }

    Usuario createUsuario(RequestCadastroUsuario requestDTO) {
        Usuario usuario = new Usuario(requestDTO);
        this.entityManager.persist(usuario);
        return usuario;
    }
}
