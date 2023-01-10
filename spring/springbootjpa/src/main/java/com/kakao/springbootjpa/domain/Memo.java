package com.kakao.springbootjpa.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Entity 클래스 생성
@Table(name="tbl_memo") //테이블 이름 설정

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mno;

    @Column(length=200, nullable = false)
    private String memoText;
}
