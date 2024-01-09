package com.example.tmdbproject.service;

import com.example.tmdbproject.check.CheckValidator;
import com.example.tmdbproject.model.ReplyEntity;
import com.example.tmdbproject.persistence.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReplyService {

    @Autowired
    CheckValidator checkValidator;

    @Autowired
    ReplyRepository replyRepository;
    public List<ReplyEntity> createReply(final ReplyEntity replyEntity) {
        checkValidator.validate(replyEntity);
        replyRepository.save(replyEntity);
        return retrieveReply(replyEntity.getMovieId());
    }
    public List<ReplyEntity> retrieveReply(int movieId){
        return replyRepository.findByMovieIdOrderByReplyDateDesc(movieId);
    }

    public List<ReplyEntity> updateReply(final ReplyEntity replyEntity) {

        ReplyEntity updateRep = ReplyEntity.builder()
                .rno(replyEntity.getRno())
                .movieId(replyEntity.getMovieId())
                .username(replyEntity.getUsername())
                .content(replyEntity.getContent())
                .replyDate(new Date())
                .build();

        replyRepository.save(updateRep);
        return retrieveReply(updateRep.getMovieId());
    }
    public List<ReplyEntity> deleteReply(int rno) {
        ReplyEntity deleteRep = replyRepository.findByRno(rno);
        checkValidator.validate(deleteRep);

        replyRepository.delete(deleteRep);

        return retrieveReply(deleteRep.getMovieId());
    }

}
