package com.auth.springsecurityjwt.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.springsecurityjwt.domain.Member;
import com.auth.springsecurityjwt.dto.MemberDTO;
import com.auth.springsecurityjwt.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Spring Security를 위한 MemberDetailsService 생성
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> result = memberRepository.findById(username);
        Member member = result.orElseThrow(() -> new UsernameNotFoundException("Cannot find memberId"));

        log.info("🛠️ MemberDetailsService member -------------------- 🛠️");

        MemberDTO memberDTO = new MemberDTO(
            member.getMemberId(),
            member.getMemberPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

        log.info("🛠️ MemberDTO =====> " + memberDTO);

        return memberDTO;
    }
}
