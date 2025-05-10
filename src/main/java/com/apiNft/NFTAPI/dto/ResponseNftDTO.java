package com.apiNft.NFTAPI.dto;

import com.apiNft.NFTAPI.entidades.Nft;

public record ResponseNftDTO(
        Long id,
        ResponseLoginUsuario user,
        String name,
        String description,
        Float price,
        Integer qtd,
        String urlMinio,
        String date) {
    public ResponseNftDTO(Nft nft) {
        this(
                nft.getId(),
                new ResponseLoginUsuario(nft.getUser().getId(), nft.getUser().getUsername()),
                nft.getName(),
                nft.getDescription(),
                nft.getPrice(),
                nft.getQtd(),
                nft.getUrlMinio(),
                nft.getCreated_at().toString());
    }
}
