package com.example.tmdbproject.persistence;

import com.example.tmdbproject.model.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    List<MemberEntity> findByUsernameOrderByUsernameAsc(String username);
    List<MemberEntity> findAllByOrderByUsernameAsc();

    MemberEntity findByUsernameAndPassword(String username, String password);

    MemberEntity findByUsername(String username);
    Optional<MemberEntity> findById(String id);

    @Transactional
    @Modifying
    @Query("update MemberEntity m set m.username = :username where m.id = :id")
    int testUpdate(@Param("username")String username, @Param("id")String id);

    @Transactional
    void deleteMemberEntityByUsername(String username);


    List<MemberEntity> findByUsernameLike(String test);



    // 아이디값 중복 확인
    boolean existsByUsername(String username);

}