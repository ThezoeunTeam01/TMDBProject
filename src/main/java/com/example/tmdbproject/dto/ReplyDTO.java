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
    private int movieId;
    private String username;
    private String content;
    private String img;

    public ReplyDTO(final ReplyEntity entity) {
        this.rno = entity.getRno();
        this.movieId = entity.getMovieId();
        this.username = entity.getUsername();
        this.content = entity.getContent();
        this.img = entity.getImg();
    }
    public static ReplyEntity replyEntity(ReplyDTO dto) {
        return ReplyEntity.builder()
                .rno(dto.getRno())
                .movieId(dto.getMovieId())
                .username(dto.getUsername())
                .content(dto.getContent())
                .img(dto.getImg())
                .build();
    }
}
