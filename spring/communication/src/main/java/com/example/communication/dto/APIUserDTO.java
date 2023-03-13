package com.example.communication.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class APIUserDTO extends User {
    private String mid;
    private String mpw;
    public APIUserDTO(String username, String password, Collection<GrantedAuthority> authorities) {
        //Spring Security는 아이디, 비번, 권한의 모임이 기본 정보
        super(username, password, authorities);
        this.mid = username;
        this.mpw = password;
    }
}
