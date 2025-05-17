package com.apiNft.NFTAPI.domain.repositories;

import com.apiNft.NFTAPI.domain.entity.Nft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NftRepository extends JpaRepository<Nft, Long> {}
