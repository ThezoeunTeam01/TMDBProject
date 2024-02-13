package com.example.tmdbproject.controller;

import com.example.tmdbproject.check.CheckValidator;
import com.example.tmdbproject.dto.ReplyDTO;
import com.example.tmdbproject.dto.ReplyLikeDTO;
import com.example.tmdbproject.dto.ResponseDTO;
import com.example.tmdbproject.model.ReplyEntity;
import com.example.tmdbproject.model.ReplyLikeEntity;
import com.example.tmdbproject.service.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequestMapping("reply")
@RestController
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @Autowired
    CheckValidator validator;

    @PostMapping("register")
    public ResponseEntity<?> createReply(@RequestBody ReplyDTO replyDTO) {
        log.info("DTO 값 확인"+replyDTO);

        validator.validate(replyDTO);

        ReplyEntity replyEntity = ReplyDTO.replyEntity(replyDTO);

        List<ReplyEntity> replyList = replyService.createReply(replyEntity);

        List<ReplyDTO> dtos = replyList.stream().map(ReplyDTO::new).collect(Collectors.toList());

        ResponseDTO<ReplyDTO> response = ResponseDTO.<ReplyDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
    @GetMapping
    public ResponseEntity<?> retrieveReply(@RequestParam String contentType, @RequestParam int contentId) {
        log.info("----------reply--------------");

        log.info(contentType+"/"+contentId);
        log.info(contentType);
        log.info(contentId);

        List<ReplyEntity> replyList = replyService.retrieveReply(contentType, contentId);

        List<ReplyDTO> response = replyList.stream().map(ReplyDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateReply(@RequestBody ReplyDTO replyDTO) {

        validator.validate(replyDTO);

        ReplyEntity replyEntity = ReplyDTO.replyEntity(replyDTO);

        List<ReplyEntity> replyList = replyService.updateReply(replyEntity);

        List<ReplyDTO> response = replyList.stream().map(ReplyDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }
    @PutMapping("updateImg")
    public void updateImg(@RequestBody ReplyDTO dto) {

        log.info("updateImg ReplyDTO-----------"+dto);
        replyService.updateImg(dto.getImg(),dto.getUsername());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReply(@RequestBody ReplyDTO replyDTO) {

        ReplyEntity replyEntity = ReplyDTO.replyEntity(replyDTO);

        List<ReplyEntity> replyList = replyService.deleteReply(replyEntity.getRno());

        List<ReplyDTO> response = replyList.stream().map(ReplyDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }
    // 댓글 좋아요
    @PostMapping("likeCreate")
    public ResponseEntity<?> likeCreate(@RequestBody ReplyLikeDTO replyLikeDTO) {

        validator.validate(replyLikeDTO);
        ReplyLikeEntity entity = replyLikeDTO.replyLikeEntity(replyLikeDTO);
        replyService.createReplyLike(entity);

        return ResponseEntity.ok().body("success");
    }
    @DeleteMapping("likeDelete")
    public ResponseEntity<?> likeDelete(@RequestBody ReplyLikeDTO replyLikeDTO) {
        log.info("likeDelete");
        validator.validate(replyLikeDTO);
        ReplyLikeEntity entity = replyLikeDTO.replyLikeEntity(replyLikeDTO);
        replyService.deleteReplyLike(entity);

        return ResponseEntity.ok().body("ok");
    }
    @GetMapping("likeList")
    public ResponseEntity<?> likeList(@RequestParam String contentType, @RequestParam int contentId, @RequestParam int rno, @RequestParam String username) {

        Optional<ReplyLikeEntity> isLike = replyService.replyLikeList(contentType, contentId, rno, username);
        Map<String,Integer> response = new HashMap<>();
        if(isLike.isEmpty()){
            response.put("status",0);

        }else{
            response.put("status",isLike.get().getId());

        }
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("likeCount")
    public ResponseEntity<?> likeCount(@RequestParam String contentType, @RequestParam int contentId, @RequestParam int rno) {

        long likeCount = replyService.replyLikeCount(contentType, contentId, rno);

        Map<String, Long> count = new HashMap<>();
        count.put("count",likeCount);

        return ResponseEntity.ok().body(count);
    }

}