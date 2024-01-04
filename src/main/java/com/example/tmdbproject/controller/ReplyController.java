package com.example.tmdbproject.controller;

import com.example.tmdbproject.dto.ReplyDTO;
import com.example.tmdbproject.dto.ResponseDTO;
import com.example.tmdbproject.model.ReplyEntity;
import com.example.tmdbproject.service.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequestMapping("reply")
@RestController
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @PostMapping
    public ResponseEntity<?> createReply(@RequestBody ReplyDTO replyDTO) {

        ReplyEntity replyEntity = ReplyDTO.replyEntity(replyDTO);

        log.info("------넘어온 entity------"+replyEntity);

        List<ReplyEntity> replyList = replyService.createReply(replyEntity);

        log.info("----ListEntity-----"+replyList);

        List<ReplyDTO> dtos = replyList.stream().map(ReplyDTO::new).collect(Collectors.toList());

        ResponseDTO<ReplyDTO> response = ResponseDTO.<ReplyDTO>builder().data(dtos).build();


        log.info("------response------"+response);

         return ResponseEntity.ok().body(response);
    }
    @GetMapping
    public ResponseEntity<?> retrieveReply() {
        int bno = 1;
        List<ReplyEntity> replyList = replyService.retrieveReply(bno);

        log.info("-----------replyList----------"+replyList);

        List<ReplyDTO> response = replyList.stream().map(ReplyDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateReply(@RequestBody ReplyDTO replyDTO) {
        ReplyEntity replyEntity = ReplyDTO.replyEntity(replyDTO);

        List<ReplyEntity> replyList = replyService.updateReply(replyEntity);

        log.info("-----putMapping List-------"+replyList);

        List<ReplyDTO> response = replyList.stream().map(ReplyDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReply(@RequestBody ReplyDTO replyDTO) {

        ReplyEntity replyEntity = ReplyDTO.replyEntity(replyDTO);

        List<ReplyEntity> replyList = replyService.deleteReply(replyEntity.getRno());

        List<ReplyDTO> response = replyList.stream().map(ReplyDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

}
