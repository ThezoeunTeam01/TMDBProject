package com.example.tmdbproject.dto;

import com.example.tmdbproject.model.ReplyEntity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {

    @Id
    private int rno;

    @NotNull
    private String contentType;
    @NotNull
    private int contentId;
    @NotNull
    private String username;

    private String reply;

    private String img;

    public ReplyDTO(final ReplyEntity entity) {
        this.rno = entity.getRno();
        this.contentType = entity.getContentType();
        this.contentId = entity.getContentId();
        this.username = entity.getUsername();
        this.reply = entity.getReply();
        this.img = entity.getImg();
    }
    public static ReplyEntity replyEntity(ReplyDTO dto) {
        return ReplyEntity.builder()
                .rno(dto.getRno())
                .contentType(dto.getContentType())
                .contentId(dto.getContentId())
                .username(dto.getUsername())
                .reply(dto.getReply())
                .img(dto.getImg())
                .build();
    }
}
