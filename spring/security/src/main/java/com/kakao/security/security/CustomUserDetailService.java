package com.kakao.security.security;

import com.kakao.security.model.ClubMember;
import com.kakao.security.persistence.ClubMemberRepository;
import com.kakao.security.security.dto.ClubMemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final ClubMemberRepository clubMemberRepository;

    //아이디를 입력하고 로그인을 요청하면 아이디에 해당하는 데이터를 찾아오는 메서드
    //로그인 처리를 여기서 해주어야 함
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUSerByUsername: " + username);

        Optional<ClubMember> result = clubMemberRepository.getWithRoles(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("없는 사용자 이름");
        }
        //존재하는 사용자 찾아오기
        ClubMember member = result.get();
        ClubMemberSecurityDTO clubMemberSecurityDTO =
                new ClubMemberSecurityDTO(member.getMpw(),
                        member.getMpw(), member.getEmail(),
                        member.getName(), member.isDel(),
                        false,
                        member.getRoleSet().stream().map(
                                memberRole -> new SimpleGrantedAuthority(
                                        "ROLE_" + memberRole.name())
                        ).collect(Collectors.toList())
                );
        return clubMemberSecurityDTO;
    }
}
