package com.example.tmdbproject.persistence;

import com.example.tmdbproject.model.LikeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity,Integer> {
    List<LikeEntity> findByUserId(int userId);

    Optional<LikeEntity> findByUserIdAndMovieId(int userId, int movieId);
}
