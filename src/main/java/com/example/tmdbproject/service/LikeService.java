package com.example.tmdbproject.service;

import com.example.tmdbproject.check.CheckValidator;
import com.example.tmdbproject.dto.LikeDTO;
import com.example.tmdbproject.model.LikeEntity;
import com.example.tmdbproject.persistence.LikeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class LikeService {

    @Autowired
    LikeRepository repository;

    @Autowired
    CheckValidator validator;

    public void LikeCreate(LikeEntity entity){

        validator.validate(entity);

        repository.save(entity);
    }
    public void LikeDelete(LikeEntity entity){
        LikeEntity list = repository.findByUserIdAndMovieId(entity.getUserId(), entity.getMovieId()).get();
        repository.delete(list);
    }
    public List<LikeEntity> LikeRead(LikeEntity entity) {
        // entity 유효성 검사
        validator.validate(entity);
        return repository.findByUserId(entity.getUserId());
    }
    public LikeEntity LikeOnlyRead(LikeEntity entity) {
        log.info("서비스영역 확인");
        validator.validate(entity);
        Optional<LikeEntity> likeEntityOptional = repository.findByUserIdAndMovieId(entity.getUserId(), entity.getMovieId());

        if (likeEntityOptional.isPresent()) {
            LikeEntity likeEntity = likeEntityOptional.get();
            log.info("반환 하는거 확인" + likeEntity);
            return likeEntity;
        } else {
            // 해당하는 레코드가 없는 경우, 원하는 대응을 수행하거나 null을 반환합니다.
            log.info("해당하는 레코드가 없습니다.");
            return new LikeEntity();
        }
    }
}
