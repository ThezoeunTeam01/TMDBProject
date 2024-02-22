package com.example.tmdbproject.controller;

import com.example.tmdbproject.dto.StarDTO;
import com.example.tmdbproject.model.StarEntity;
import com.example.tmdbproject.service.StarService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("star_rating")
public class StarController {

    @Autowired
    StarService starService;

    @PostMapping("/create_rating")
    public StarDTO createStarRating(@RequestBody StarDTO starDTO) {

        StarEntity starEntity = starDTO.starEntity(starDTO);
        log.info(starEntity);

        // DB에서 받은 내용을 StarEntity로 변환하여 StarService에 전달
        StarEntity savedEntity = starService.createStar(starEntity);

        // DB에서 저장된 StarEntity를 DTO로 변환하여 리턴
        return new StarDTO(savedEntity);
    }

    @GetMapping("/retrieve_rating")
    public ResponseEntity<?> retrieveStarRating(@RequestParam String id,
                                                @RequestParam String contentType,
                                                @RequestParam int contentId,
                                                @RequestParam String username) {

        StarEntity starEntity = starService.retrieveStar(id, contentType, contentId, username);
        log.info("retrieveStarRating:" + starEntity);

        if(starEntity != null) {
            StarDTO stdto = new StarDTO(starEntity);
            return ResponseEntity.ok().body(stdto);
        } else {
            // 데이터가 없을 경우 빈 객체 반환
            return ResponseEntity.ok().body(new HashMap<>());
        }
    }


    @PutMapping("/update_rating")
    public ResponseEntity<?> updateStarRating(@RequestBody StarDTO starDTO) {

        // starDTO로부터 StarEntity 생성
        StarEntity starEntity = StarDTO.starEntity(starDTO);

        // StarEntity 업데이트
        starService.updateStar(starEntity);

        return ResponseEntity.ok().body(new StarDTO(starEntity));
    }


    @DeleteMapping("/delete_rating")
    public ResponseEntity<?> deleteStarRating(@RequestBody StarDTO starDTO) {

        StarEntity starEntity = StarDTO.starEntity(starDTO);

        StarEntity deletedEntity = starService.deleteStar(starEntity.getContentType(),
                starEntity.getContentId(), starEntity.getUsername());

        if (deletedEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조건에 맞는 별점 값을 찾을 수 없습니다.");
        }

        return ResponseEntity.ok().body(new StarDTO(deletedEntity));
    }

    @GetMapping("/get_ratings")
    public ResponseEntity<?> getStarRatings(@RequestParam String contentType, @RequestParam int contentId) {
        contentType = contentType.trim();
        log.info("getStarRatings:" + contentType + "," + contentId);
        List<StarEntity> starEntities = starService.getStarRatings(contentType, contentId);
        log.info("getStarRatings-entity:" + starEntities);
        return ResponseEntity.ok().body(starEntities);
    }
}
