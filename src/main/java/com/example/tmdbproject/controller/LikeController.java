package com.example.tmdbproject.controller;

import com.example.tmdbproject.check.CheckValidator;
import com.example.tmdbproject.dto.LikeDTO;
import com.example.tmdbproject.dto.ResponseDTO;
import com.example.tmdbproject.model.LikeEntity;
import com.example.tmdbproject.service.LikeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    LikeService service;

    @Autowired
    CheckValidator validator;

    @PostMapping("/likeCreate")
    public void likeCreate(@RequestBody LikeDTO dto){
        log.info("라이크 크리에이트 시작");
        validator.validate(dto);
        log.info("받은 dto 값 확인"+dto);

        LikeEntity likeEntity = LikeDTO.toEntity(dto);

        service.LikeCreate(likeEntity);

    }
    @PostMapping("/likeDelete")
    public void likeDelete(@RequestBody LikeDTO dto){
        log.info("라이크 딜리트 시작");
        // dto 유효성 검사
        validator.validate(dto);
        log.info("받은 dto 값 확인"+dto);

        LikeEntity entity = LikeDTO.toEntity(dto);

        service.LikeDelete(entity);
    }

    @PostMapping("/likeRead")
    public ResponseEntity<?> likeRead(@RequestBody LikeDTO dto){

        log.info("라이크 리드 시작");
        // dto 유효성 검사
        validator.validate(dto);
        log.info("받은 dto 값 확인" + dto);
        LikeEntity entity = LikeDTO.toEntity(dto);

        List<LikeEntity> likeEntities = service.LikeRead(entity);
        log.info("출력되는 리스트"+likeEntities);

        List<LikeDTO> dtos = likeEntities.stream().map(LikeDTO::new).collect(Collectors.toList());
        ResponseDTO<LikeDTO> response = ResponseDTO.<LikeDTO>builder().data(dtos).build();

        log.info("--------response-------"+response);

        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/likeReadButton")
    public ResponseEntity<?> likeReadButton(@RequestBody LikeDTO dto){
        log.info("라이크 리드 버튼 시작");
        // dto 유효성 검사
        validator.validate(dto);
        log.info("받은 dto 값 확인" + dto);
        LikeEntity entity = LikeDTO.toEntity(dto);
        log.info("변환한 엔티티값 확인" + dto);
        LikeEntity likeEntities = service.LikeOnlyRead(entity);
        log.info("출력되는 리스트"+likeEntities);
        LikeDTO response = new LikeDTO(likeEntities);
        log.info("--------response-------"+response);
        return ResponseEntity.ok().body(response);
    }

}
