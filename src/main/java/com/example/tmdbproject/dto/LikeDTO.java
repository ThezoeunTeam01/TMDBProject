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
    private int userId;
    private int movieId;

    public LikeDTO(final LikeEntity entity){
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.movieId = entity.getMovieId();
    }

    public static LikeEntity toEntity(final LikeDTO dto){
        return LikeEntity.builder()
                .userId(dto.getUserId())
                .movieId(dto.getMovieId())
                .build();
    }
}
