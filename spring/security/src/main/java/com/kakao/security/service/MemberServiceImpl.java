package com.kakao.security.service;

import com.kakao.security.dto.ClubMemberJoinDTO;
import com.kakao.security.model.ClubMember;
import com.kakao.security.model.ClubMemberRole;
import com.kakao.security.persistence.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void join(ClubMemberJoinDTO memberJoinDTO) throws MidExistException {
        //아이디 중복 확인
        String mid = memberJoinDTO.getMid();
        boolean exist = clubMemberRepository.existsById(mid);
        if(exist) {
            throw new MidExistException();
        }
        //회원 가입을 위해서 입력받은 정보를 가지고 ClubMember Entity를 생성
        ClubMember member = ClubMember.builder()
                .mid(memberJoinDTO.getMid())
                .mpw(memberJoinDTO.getMpw())
                .email(memberJoinDTO.getEmail())
                .name(memberJoinDTO.getName())
                .del(memberJoinDTO.isDel())
                .social(memberJoinDTO.isSocial())
                .build();
        //비밀번호 암호화
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        //권한 설정
        member.addRole(ClubMemberRole.USER);
        log.info(member);
        log.info(member.getRoleSet());

        clubMemberRepository.save(member);
    }
}
