package com.apiNft.NFTAPI.controllers;

import com.apiNft.NFTAPI.Repositories.CommentRepository;
import com.apiNft.NFTAPI.Repositories.NftRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroNft;
import com.apiNft.NFTAPI.entidades.Comment;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import com.apiNft.NFTAPI.services.NftService;
import com.apiNft.NFTAPI.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:4200")
@RestController
@RequestMapping("/api/nft")
public class NftController {

    @Autowired
    private NftRepository nftRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NftService nftService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping //Retorna todos os nfts
    public ResponseEntity<List<Nft>> findAll(){
        List<Nft> nfts = nftService.getAll();
        return ResponseEntity.ok(nfts);
    }

    @GetMapping("/{id}") //Retorna um nft
    public ResponseEntity<Nft> findById(@PathVariable Long id){
        Nft nft = nftService.getById(id);
        return ResponseEntity.ok(nft);
    }

    @GetMapping("comments/{id}") //Retorna todos os comentários de um nft
    public List<Comment> pegarComentarios(@PathVariable Long id){
        return nftRepository.getReferenceById(id).getComment();
    }

//    @PostMapping //Cria um nft
//    public ResponseEntity inserirNft(@RequestBody @Valid DadosCadastroNft nft, UriComponentsBuilder uriBuilder){
//        Nft newNft = nftService.postNft(nft);
//        URI uri = uriBuilder.path("/nft/{id}").buildAndExpand(newNft.getId()).toUri();
//        return ResponseEntity.created(uri).body(newNft);
//    }

    @Transactional
    @PostMapping("/usuario/{id}")
    public ResponseEntity<Nft> postNft(@PathVariable Long id, @RequestBody RequestCadastroNft nft){
        Nft newNft = nftService.postNft(id, nft);
        return ResponseEntity.ok(newNft);
    }


    @PostMapping("/{id}") //Cria um comentário em um nft
    public void inserirComentario(@PathVariable Long id, @RequestBody Comment comment){
        Nft nft = nftRepository.getReferenceById(id);

        var comentario = new Comment(nft, comment.getUsuario(), comment.getComentario());

        commentRepository.save(comentario);
    }

    @PutMapping("/atualizar/{id}") //Atualiza um nft
    public Nft replaceNft(@PathVariable Long id, @RequestBody Nft nft){
        return nftRepository.findById(id).map(campo -> {
            campo.setName(nft.getName());
            campo.setDescription(nft.getDescription());
            campo.setPrice(nft.getPrice());
            campo.setQtd(nft.getQtd());
            return nftRepository.save(campo);
        }).orElseGet(() -> {
            return nftRepository.save(nft);
        });
    }

    @DeleteMapping //Deleta um nft
    public void delete(@RequestBody Nft nft){
        nftRepository.delete(nft);
    }

    @GetMapping("/canedit/nft/{idNft}/usuario/{idUser}")
    public LogadoDTO verificaAuth(@PathVariable Long idNft, @PathVariable Long idUser){
        Nft nft = this.nftService.getById(idNft);
        Usuario usuario = this.usuarioService.getUser(idUser);
        if(nft.getUser().equals(usuario)){
            return new LogadoDTO(true);
        }else{
            return new LogadoDTO(false);
        }
    }

    public record LogadoDTO(boolean logado){}
}
