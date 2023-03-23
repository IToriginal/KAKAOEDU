package com.auth.springsecurityjwt.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Spring Security를 위한 DTO 클래스 생성
 */
@Getter
@Setter
@ToString
public class MemberDTO extends User {

    private String memberId;
    private String memberPassword;

    public MemberDTO(String username, String password, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.memberId = username;
        this.memberPassword = password;
    }
}
