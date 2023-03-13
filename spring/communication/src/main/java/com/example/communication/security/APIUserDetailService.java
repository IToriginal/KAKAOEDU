package com.example.communication.security;

import com.example.communication.domain.APIUser;
import com.example.communication.dto.APIUserDTO;
import com.example.communication.persistence.APIUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class APIUserDetailService implements UserDetailsService {
    private final APIUserRepository apiUserRepository;
    //로그인을 처리해주는 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User 찾아오기
        Optional<APIUser> result = apiUserRepository.findById(username);
        //유저 정보를 가져오는데 없는 경우에는 예외를 발생시킴
        APIUser apiUser = result.orElseThrow(() ->
                new UsernameNotFoundException("아이디가 잘못되었습니다.")
        );
        //로그인 성공에 대한 처리
        log.info("apiUser ------------------- ");
        APIUserDTO dto = new APIUserDTO(
                apiUser.getMid(), apiUser.getMpw(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        log.info(dto);
        return dto;
    }
}
