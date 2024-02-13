package com.example.tmdbproject.service;

import com.example.tmdbproject.model.MemberEntity;
import com.example.tmdbproject.persistence.MemberRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Member;

@Log4j2
@Service
public class KakaoService {

    MemberRepository memberRepository;

    RestTemplate restTemplate = new RestTemplate();
    public String kakaoToken(String code) {

        try {

            // 카카오 토큰 요청 API URL
            String url = "https://kauth.kakao.com/oauth/token";

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 파라미터 설정
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("grant_type", "authorization_code");
            parameters.add("client_id", "01074d8866978b72ce430120a459bdf7"); // 카카오 앱의 REST API 키
            parameters.add("redirect_uri", "http://localhost:3001"); // 카카오 로그인 후 리다이렉트될 URI
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

    public MemberEntity kakaoUser(String accessToken) {

        try {
            String url = "https://kapi.kakao.com/v2/user/me";

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken); // 카카오 액세스 토큰

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            // GET 요청 보내기
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            ObjectMapper mapper = new ObjectMapper();

            // 응답 본문을 JsonNode로 변환
            JsonNode jsonNode = mapper.readTree(responseEntity.getBody());

            log.info(responseEntity.getBody());

            // access_token 값 추출
            JsonNode properties = jsonNode.get("properties");

            String username = properties.get("nickname").asText();

            MemberEntity memberEntity = memberRepository.findByUsername(username);

            return memberEntity;

        }catch (Exception e) {
            return null;
        }
    }
}
