package com.example.tmdbproject.dto;

import com.example.tmdbproject.model.LikeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private int id;
    private String userId;
    private String contentType;
    private int contentId;

    public LikeDTO(final LikeEntity entity){
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.contentType = entity.getContentType();
        this.contentId = entity.getContentId();
    }

    public static LikeEntity toEntity(final LikeDTO dto){
        return LikeEntity.builder()
                .userId(dto.getUserId())
                .contentType(dto.getContentType())
                .contentId(dto.getContentId())
                .build();
    }
}