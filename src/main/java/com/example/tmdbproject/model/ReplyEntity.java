package com.example.tmdbproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reply")
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "your_sequence_generator")
    @SequenceGenerator(name = "your_sequence_generator", sequenceName = "your_sequence_name", allocationSize = 1)
    private int rno;
    private int movieId;
    @NotNull
    private String username;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date replyDate;

    private String img;

    @PrePersist
    protected void onCreate() {
        replyDate = new Date();
    }
}
