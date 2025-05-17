package com.apiNft.NFTAPI.domain.repositories;

import com.apiNft.NFTAPI.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
