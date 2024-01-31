package com.example.tmdbproject.persistence;

import com.example.tmdbproject.model.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity,Integer> {

    List<ReplyEntity> findByContentTypeAndContentIdOrderByRnoDesc(String contentType,int contentId);

    ReplyEntity findByRno(int rno);

    @Modifying
    @Query("update ReplyEntity r set r.img= :img where r.username = :username")
    void updateImgByUsername(@Param("img") String img, @Param("username") String username);
}
