package com.kakao.security.persistence;

import com.kakao.security.model.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
    //mid를 매개변수로 받아서 social의 값이 false 인 데이터를 전부 찾아오는 메서드
    //SQL
    //SELECT *
    //FROM club_member m, club_member_role_set s
    //WHERE m.mid = s.mid and m.mid=? and m.social=false

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from ClubMember m " +
            "where m.mid = :mid and m.social = false")
    Optional<ClubMember> getWithRoles(String mid);

    @EntityGraph(attributePaths = "roleSet",type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.email = :email")
    Optional<ClubMember> findByEmail(@Param("email") String email);
}
