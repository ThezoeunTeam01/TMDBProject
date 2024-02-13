package com.example.tmdbproject.persistence;

import com.example.tmdbproject.model.ReplyLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyLikeRepository extends JpaRepository<ReplyLikeEntity,Integer> {
    Optional<ReplyLikeEntity> findIdByContentTypeAndContentIdAndRnoAndUsername(String contentType, int contentId, int rno, String username);

    long countByContentTypeAndContentIdAndRno(String contentType, int contentId, int rno);
}
