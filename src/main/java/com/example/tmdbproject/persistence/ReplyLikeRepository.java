package com.example.tmdbproject.persistence;

import com.example.tmdbproject.model.ReplyLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLikeEntity,Integer> {
    long countByContentTypeAndContentIdAndRnoAndUsername(String contentType, int contentId, int rno, String username);
}
