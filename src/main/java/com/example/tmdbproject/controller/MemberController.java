package com.example.tmdbproject.controller;

import com.example.tmdbproject.check.CheckValidator;
import com.example.tmdbproject.dto.LoginDTO;
import com.example.tmdbproject.dto.MemberDTO;
import com.example.tmdbproject.model.MemberEntity;
import com.example.tmdbproject.security.TokenProvider;
import com.example.tmdbproject.service.KakaoService;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    KakaoService kakaoService;

    @Autowired
    CheckValidator checkValidator;

    @Autowired
    TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @PostMapping("register")
    public ResponseEntity<?>createMember(@RequestBody MemberDTO dto) {

        try{
            // 유효성 검사
            checkValidator.validate(dto);
            
            // 비밀번호 암호화
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));

            MemberEntity memberEntity = MemberDTO.memberEntity(dto);

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
    @GetMapping("doubleCheck")
    public ResponseEntity<?> doubleCheck(@RequestParam String username){
        log.info("doubleCheck");
        long isDouble = memberService.doubleCheck(username);
        Map<String,String> response = new HashMap<>();
        if(isDouble == 1){
            response.put("status","impossible");
        }else {
            response.put("status","possible");
        }
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("socialLogin")
    public ResponseEntity<?> socialLogin(@RequestParam String code) {
        try{
            log.info("code"+code);

            RestTemplate restTemplate = new RestTemplate();

            String accessToken = kakaoService.kakaoToken(code);
            log.info("accessToken"+accessToken);
            MemberEntity memberEntity =  kakaoService.kakaoUser(accessToken);

            log.info(memberEntity);

            if(memberEntity.getId()==null) {
                Map<String, Object> response = new HashMap<>();
                response.put("status","not-exist");
                response.put("email",memberEntity.getEmail());
               return ResponseEntity.ok().body(response);
            }else {
                final String token = tokenProvider.create(memberEntity);

                final LoginDTO loginDTO = LoginDTO.builder()
                        .username(memberEntity.getUsername())
                        .id(memberEntity.getId())
                        .img(memberEntity.getImg())
                        .token(token)
                        .build();
                log.info("----loginDTO----"+loginDTO);
                return ResponseEntity.ok().body(loginDTO);
            }

        }catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.badRequest().body("error");
        }
    }
}
