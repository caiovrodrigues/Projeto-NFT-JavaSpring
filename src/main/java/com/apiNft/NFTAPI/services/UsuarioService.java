package com.apiNft.NFTAPI.services;

import com.apiNft.NFTAPI.Repositories.UsuarioRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroUsuario;
import com.apiNft.NFTAPI.dto.RequestLoginUsuario;
import com.apiNft.NFTAPI.dto.ResponseLoginUsuario;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.InputMismatchException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario getUser(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com o id: " + id + " não encontrado."));
    }

    public List<Nft> getAllNftsFromUser(Long id) {
        Usuario usuario = this.getUser(id);
        return usuario.getNfts();
    }

    @Transactional
    public Usuario postUser(RequestCadastroUsuario usuario) {
        if(!usuario.senha().equals(usuario.confirmaSenha())){
            throw new InputMismatchException("Senha e confirma senha não conferem");
        }
        return repository.save(new Usuario(usuario));
    }

    @Transactional(readOnly = true)
    public ResponseLoginUsuario logarUser(RequestLoginUsuario dados) {
        Usuario user = repository.findByUsername(dados.username()).orElseThrow(() -> new EntityNotFoundException("O usuário não existe"));
        if(!user.getPassword().equals(dados.password())){
            throw new InputMismatchException("A senha está incorreta");
        }
        System.out.println(user.toString());
        return new ResponseLoginUsuario(user.getId(), user.getUsername());
    }
}
