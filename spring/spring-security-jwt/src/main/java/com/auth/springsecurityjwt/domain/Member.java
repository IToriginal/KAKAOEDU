package com.auth.springsecurityjwt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 사용자 정보를 위한 테이블 생성
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    @Id
    private String memberId;
    private String memberPassword;

    public void changeMemberPassWord(String memberPassword) {
        this.memberPassword = memberPassword;
    }
}
