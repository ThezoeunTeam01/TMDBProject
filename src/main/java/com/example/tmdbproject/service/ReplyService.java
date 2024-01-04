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
        return retrieveReply(replyEntity.getBno());
    }
    public List<ReplyEntity> retrieveReply(int bno){
        return replyRepository.findByBnoOrderByReplyDateDesc(bno);
    }

    public List<ReplyEntity> updateReply(final ReplyEntity replyEntity) {

        ReplyEntity updateRep = ReplyEntity.builder()
                .rno(replyEntity.getRno())
                .bno(replyEntity.getBno())
                .userName(replyEntity.getUserName())
                .content(replyEntity.getContent())
                .replyDate(new Date())
                .build();

        replyRepository.save(updateRep);
        return retrieveReply(updateRep.getBno());
    }
    public List<ReplyEntity> deleteReply(int rno) {
        ReplyEntity deleteRep = replyRepository.findByRno(rno);
        checkValidator.validate(deleteRep);

        replyRepository.delete(deleteRep);

        return retrieveReply(deleteRep.getBno());
    }

}
