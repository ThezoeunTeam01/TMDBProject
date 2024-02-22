package com.example.tmdbproject.persistence;

import com.example.tmdbproject.model.StarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<StarEntity, Integer> {

    Optional<StarEntity> findByIdAndContentTypeAndContentIdAndUsernameAndIsDeletedFalse(
            String id, String contentType, int contentId, String username
    );

    StarEntity findByContentTypeAndContentIdAndUsernameAndIsDeletedFalse(String contentType, int contentId, String username);

    List<StarEntity> findByContentTypeAndContentIdAndIsDeletedFalse(String contentType, int contentId);

}

