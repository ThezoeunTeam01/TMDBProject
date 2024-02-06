package com.example.tmdbproject.controller;

import com.example.tmdbproject.check.CheckValidator;
import com.example.tmdbproject.dto.LoginDTO;
import com.example.tmdbproject.dto.MemberDTO;
import com.example.tmdbproject.model.MemberEntity;
import com.example.tmdbproject.security.TokenProvider;
import com.example.tmdbproject.service.MemberService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Log4j2
@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    CheckValidator checkValidator;

   @Autowired
   TokenProvider tokenProvider;

   private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @PostMapping("register")
    public ResponseEntity<?>createMember(@RequestBody MemberDTO dto) {

        try{
            checkValidator.validate(dto);
            log.info("----------dto 정보 확인-----------"+dto);

            dto.setPassword(passwordEncoder.encode(dto.getPassword()));

            MemberEntity memberEntity = MemberDTO.memberEntity(dto);

            log.info("entity입니다"+memberEntity);

            memberEntity = memberService.createMember(memberEntity);

            MemberDTO memberDTO = new MemberDTO(memberEntity);

            return ResponseEntity.ok().body(memberDTO);
        }catch (Exception e) {
            String error = e.getMessage();

            return ResponseEntity.badRequest().body(error);
        }
    }
    @GetMapping
    public ResponseEntity<?> retrieveMember(@RequestParam String id) {
        log.info("react와 연결");
        try{
            MemberEntity memberEntity = memberService.retrieveMember(id);

            MemberDTO memberDTO = new MemberDTO(memberEntity);

            return ResponseEntity.ok().body(memberDTO);
        }catch (Exception e) {

            String error = e.getMessage();

            return ResponseEntity.badRequest().body(error);
        }
    }
    @PutMapping
    public ResponseEntity<?> updateMember(@RequestBody MemberDTO dto) {

        try {

            dto.setPassword(passwordEncoder.encode(dto.getPassword()));

            MemberEntity memberEntity = MemberDTO.memberEntity(dto);

            memberEntity = memberService.updateMember(memberEntity);

            MemberDTO memberDTO = new MemberDTO(memberEntity);

            return ResponseEntity.ok().body(memberDTO);

        } catch (Exception e) {

            String error = e.getMessage();

            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMember(@RequestParam String id) {
        try{

            memberService.deleteMember(id);

            return ResponseEntity.ok().body("success delete");

        }catch (Exception e) {

            String error = e.getMessage();

            return ResponseEntity.badRequest().body(error);
        }
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO dto) {
        MemberEntity member = memberService.getByCredentials(dto.getUsername(),dto.getPassword(), passwordEncoder);
        log.info(member);

        if(member != null) {
            final String token = tokenProvider.create(member);

            final LoginDTO loginDTO = LoginDTO.builder()
                    .username(member.getUsername())
                    .id(member.getId())
                    .img(member.getImg())
                    .token(token)
                    .build();
            log.info("----loginDTO----"+loginDTO);
            return ResponseEntity.ok().body(loginDTO);
        }else{
            return ResponseEntity.ok().body(dto);
        }
    }
    @GetMapping("socialLogin")
    public ResponseEntity<?> socialLogin(@RequestParam String code) {
        try{

            RestTemplate restTemplate = new RestTemplate();

            String accessToken = tokenProvider.kakaoToken(code);

            String url = "https://kapi.kakao.com/v2/user/me";

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken); // 카카오 액세스 토큰

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            // GET 요청 보내기
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // 응답 받기
            log.info("Response: " + responseEntity.getBody());

            return ResponseEntity.ok().body("ss");
        }catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.ok().body("dd");
        }
    }
}
