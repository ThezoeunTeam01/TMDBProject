package com.example.tmdbproject.service;

import com.example.tmdbproject.model.StarEntity;
import com.example.tmdbproject.persistence.StarRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class StarService {

    @Autowired
    StarRepository starRepository;

    public StarEntity createStar(final StarEntity starEntity) {
        log.info("createStar로 들어오는 엔티티값:"+starEntity);
        StarEntity savedEntity = starRepository.save(starEntity);
        log.info("StarService-createStar:"+savedEntity);
        return savedEntity;
    }

    public StarEntity retrieveStar(String id, String contentType, int contentId, String username) {
        id = id.trim(); // 공백 제거
        log.info("retrieveStar: " + id + ", " + contentType + ", " + contentId + ", " + username);
        Optional<StarEntity> result = starRepository.findByIdAndContentTypeAndContentIdAndUsernameAndIsDeletedFalse(
                id, contentType, contentId, username
        );
        log.info("StarService-retrieveStar:" + result);
        if (result.isPresent()) {
            return result.get();
        } else {
            // 데이터가 없을 경우 null 반환
            return null;
        }
    }


    public StarEntity updateStar(final StarEntity starEntity) {
        starRepository.save(starEntity);
        return starEntity;
    }

    public StarEntity deleteStar(String contentType, int contentId, String username) {
        StarEntity deleteStar = starRepository.findByContentTypeAndContentIdAndUsernameAndIsDeletedFalse(contentType, contentId, username);
        if (deleteStar != null) {
            deleteStar.setDeleted(true);
            starRepository.save(deleteStar);
        }
        return deleteStar;
    }

    public List<StarEntity> getStarRatings(String contentType, int contentId) {
        contentType = contentType.trim();
        return starRepository.findByContentTypeAndContentIdAndIsDeletedFalse(contentType, contentId);
    }
}

