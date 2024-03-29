package com.example.tmdbproject.service;

import com.example.tmdbproject.check.CheckValidator;
import com.example.tmdbproject.model.ReplyEntity;
import com.example.tmdbproject.model.ReplyLikeEntity;
import com.example.tmdbproject.persistence.ReplyLikeRepository;
import com.example.tmdbproject.persistence.ReplyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {


    @Autowired
    ReplyRepository replyRepository;
    public List<ReplyEntity> createReply(final ReplyEntity replyEntity) {
        replyRepository.save(replyEntity);
        return retrieveReply(replyEntity.getContentType(),replyEntity.getContentId());
    }
    public List<ReplyEntity> retrieveReply(String contentType, int contentId){
        return replyRepository.findByContentTypeAndContentIdOrderByRnoDesc(contentType, contentId);
    }

    public List<ReplyEntity> updateReply(final ReplyEntity replyEntity) {

        ReplyEntity updateRep = ReplyEntity.builder()
                .rno(replyEntity.getRno())
                .contentType(replyEntity.getContentType())
                .contentId(replyEntity.getContentId())
                .username(replyEntity.getUsername())
                .reply(replyEntity.getReply())
                .img(replyEntity.getImg())
                .replyDate(new Date())
                .build();

        replyRepository.save(updateRep);
        return replyRepository.findByContentTypeAndContentIdOrderByRnoDesc(updateRep.getContentType(), updateRep.getContentId());
    }

    @Transactional
    public void updateImg(final String img, final String username) {
        replyRepository.updateImgByUsername(img, username);
    }

    public List<ReplyEntity> deleteReply(int rno) {
        ReplyEntity deleteRep = replyRepository.findByRno(rno);

        replyRepository.delete(deleteRep);

        return retrieveReply(deleteRep.getContentType(),deleteRep.getContentId());
    }

    @Autowired
    ReplyLikeRepository replyLikeRepository;
    public void createReplyLike(ReplyLikeEntity entity) {

        replyLikeRepository.save(entity);
    }

    public void deleteReplyLike(ReplyLikeEntity entity) {
        Optional<ReplyLikeEntity> deleteEntity =  replyLikeRepository.findIdByContentTypeAndContentIdAndRnoAndUsername(entity.getContentType(), entity.getContentId(), entity.getRno(), entity.getUsername());
        if(deleteEntity.isPresent()){
            replyLikeRepository.delete(deleteEntity.get());
        }

    }

    public Optional<ReplyLikeEntity> replyLikeList(String contentType, int contentid, int rno, String username) {
        Optional<ReplyLikeEntity> isLike = replyLikeRepository.findIdByContentTypeAndContentIdAndRnoAndUsername(contentType, contentid, rno, username);
        return isLike;
    }

    public long replyLikeCount(String contentType, int contentId, int rno) {
        long likeCount = replyLikeRepository.countByContentTypeAndContentIdAndRno(contentType, contentId, rno);
        return likeCount;
    }

}
