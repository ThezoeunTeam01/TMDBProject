package com.example.tmdbproject.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseDTO<T> {
    private List<T> data;
    private String error;
}