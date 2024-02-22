package com.example.tmdbproject.dto;

import com.example.tmdbproject.model.StarEntity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarDTO {

    @Id
    private int rno;

    private String id;

    @NotNull
    private int contentId;

    @NotNull
    private String contentType;

    @NotNull
    private String username;

    @NotNull
    private int score;

    private boolean isDeleted; // 추가된 필드

    public StarDTO(final StarEntity entity) {
        this.rno = entity.getRno();
        this.id = entity.getId();
        this.contentType = entity.getContentType();
        this.contentId = entity.getContentId();
        this.username = entity.getUsername();
        this.score = entity.getScore();
        this.isDeleted = entity.isDeleted(); // 추가된 코드
    }

    public static StarEntity starEntity(StarDTO dto) {
        return StarEntity.builder()
                .rno(dto.getRno())
                .id(dto.getId())
                .contentType(dto.getContentType())
                .contentId(dto.getContentId())
                .username(dto.getUsername())
                .score(dto.getScore())
                .isDeleted(dto.isDeleted()) // 추가된 코드
                .build();
    }
}

