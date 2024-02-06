package com.example.tmdbproject.security;

import com.example.tmdbproject.model.MemberEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    // 유저 정보를 받아 JWT를 생성함
    private static String SECRET_KEY = "FlRpX30pMqDbiAkmlfArbrmVkDD4RqISskGZmBFax5oGVxzXXWUzTR5JyskiHMIV9M1Oicegkpi46AdvrcX1E6CmTUBc6IFbTPiD";

    public String create(MemberEntity memberEntity) {
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));

        // JWT Token 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setSubject(memberEntity.getId())
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String kakaoToken(String code) {

        try {

            RestTemplate restTemplate = new RestTemplate();

            // 카카오 토큰 요청 API URL
            String url = "https://kauth.kakao.com/oauth/token";

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 파라미터 설정
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("grant_type", "authorization_code");
            parameters.add("client_id", "01074d8866978b72ce430120a459bdf7"); // 카카오 앱의 REST API 키
            parameters.add("redirect_uri", "http://localhost:3001/kakao"); // 카카오 로그인 후 리다이렉트될 URI
            parameters.add("code", code); // React에서 받은 인가코드

            // HttpEntity 생성
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

            // POST 요청 보내기
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            ObjectMapper mapper = new ObjectMapper();

            // 응답 본문을 JsonNode로 변환
            JsonNode jsonNode = mapper.readTree(responseEntity.getBody());

            // access_token 값 추출
            String accessToken = jsonNode.get("access_token").asText();

            return accessToken;
        } catch (Exception e) {
            return "error";
        }
    }
}
