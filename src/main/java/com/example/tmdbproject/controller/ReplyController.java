package com.example.tmdbproject.controller;

import com.example.tmdbproject.check.CheckValidator;
import com.example.tmdbproject.dto.ReplyDTO;
import com.example.tmdbproject.dto.ResponseDTO;
import com.example.tmdbproject.model.ReplyEntity;
import com.example.tmdbproject.service.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        log.info("----------get--------------");
        contentType = "movie";
        contentId = 1234;

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

}
