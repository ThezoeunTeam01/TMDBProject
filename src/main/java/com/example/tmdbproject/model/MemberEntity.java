package com.example.tmdbproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String gender;

    @Min(value = 10, message = "나이는 최소 10 이상이여야합니다.")
    @NotNull
    private int regidentNumber;

    @Email
    @NotNull
    private String email;

    private String sns; // 일단 넣어둠
    private String img; // 일단 넣어둠
}
