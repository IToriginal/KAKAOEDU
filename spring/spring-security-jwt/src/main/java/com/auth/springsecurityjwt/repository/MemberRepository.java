package com.auth.springsecurityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.springsecurityjwt.domain.Member;

/**
 * 사용자 정보를 위한 Repository 인터페이스 생성
 */
public interface MemberRepository extends JpaRepository<Member, String> {
}
