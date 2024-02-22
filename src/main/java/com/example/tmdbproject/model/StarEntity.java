package com.example.tmdbproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "starRating")
public class StarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
