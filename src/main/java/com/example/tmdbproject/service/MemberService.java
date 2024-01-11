package com.example.tmdbproject.service;

import com.example.tmdbproject.check.CheckValidator;
import com.example.tmdbproject.dto.MemberDTO;
import com.example.tmdbproject.model.MemberEntity;
import com.example.tmdbproject.persistence.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CheckValidator checkValidator;

    public MemberEntity createMember(MemberEntity entity) {

        checkValidator.validate(entity);

        entity.setId(null);

        return memberRepository.save(entity);
    }

    public MemberEntity retrieveMember(String id) {

        MemberEntity member = memberRepository.findById(id).get();

        checkValidator.validate(member);

        return member;
    }

    // 로그인 인증
    public MemberEntity getByCredentials(final String username, String password, PasswordEncoder passwordEncoder) {

        MemberEntity memberEntity = memberRepository.findByUsername(username);

        if(memberEntity != null && passwordEncoder.matches(password,memberEntity.getPassword())){
            return memberEntity;
        }
        return null;
    }

    public MemberEntity updateMember(MemberEntity entity) {
        System.out.println("--------entity-------"+entity);
        MemberEntity memberEntity = memberRepository.findByUsername(entity.getUsername());
        System.out.println(memberEntity);

        memberEntity.setPassword(entity.getPassword());
        memberEntity.setEmail(entity.getEmail());
        memberEntity.setRegidentNumber(entity.getRegidentNumber());
        memberEntity.setGender(entity.getGender());
        memberEntity.setSns(entity.getSns());
        memberEntity.setImg(entity.getImg());

        checkValidator.validate(memberEntity);

        return memberRepository.save(memberEntity);
    }

    public void deleteMember(String id) {
        MemberEntity memberEntity = memberRepository.findById(id).get();
        System.out.println("----------delete entitiy-------"+memberEntity);

        checkValidator.validate(memberEntity);

        memberRepository.delete(memberEntity);
    }

}
