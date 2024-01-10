package com.example.tmdbproject.persistence;

import com.example.tmdbproject.model.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity,Integer> {

    List<ReplyEntity> findByMovieIdOrderByRnoDesc(int movieId);

    ReplyEntity findByRno(int rno);
}
