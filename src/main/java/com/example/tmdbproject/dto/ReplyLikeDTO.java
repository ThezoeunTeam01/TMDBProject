package com.example.tmdbproject.dto;

import com.example.tmdbproject.model.ReplyLikeEntity;
import lombok.Data;

@Data
public class ReplyLikeDTO {
    private String contentType;
    private int contentId;
    private int rno;
    private String username;

    public ReplyLikeEntity replyLikeEntity(ReplyLikeDTO dto) {
        return ReplyLikeEntity.builder()
                .contentType(dto.getContentType())
                .contentId(dto.getContentId())
                .rno(dto.getRno())
                .username(dto.getUsername())
                .build();
    }
}