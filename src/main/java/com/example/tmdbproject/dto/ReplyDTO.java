package com.example.tmdbproject.dto;

import com.example.tmdbproject.model.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {

    private int rno;
    private int bno;
    private String userName;
    private String content;

    public ReplyDTO(final ReplyEntity entity) {
        this.rno = entity.getRno();
        this.bno = entity.getBno();
        this.userName = entity.getUserName();
        this.content = entity.getContent();
    }
    public static ReplyEntity replyEntity(ReplyDTO dto) {
        return ReplyEntity.builder()
                .rno(dto.getRno())
                .bno(dto.getBno())
                .userName(dto.getUserName())
                .content(dto.getContent())
                .build();
    }
}
