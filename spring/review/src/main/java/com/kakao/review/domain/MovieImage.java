package com.kakao.review.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

// ToString을 할 때 movie는 제외
//지연 생성이기 때문에 get을 하지 않은 상태에서 toString을 호출하면
//NullPointerException 이 발생
@ToString(exclude = "movie")

//부모 테이블을 생성 할 때 이 속성의 값을 포함 시켜 생성
@Embeddable
public class MovieImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;
    // 파일이 이름이 겹치지 않도록 하기 위해서 추가
    private String uuid;
    // 파일 이름
    private String imgName;
    // 하나의 디렉토리에 너무 많은 파일이 저장되지 않도록
    // 업로드 한 날짜 별로 파일을 기록하기 위한 디렉토리 이름
    private String path;
    // 하나의 Movie가 여러 개의 MovieImage를 소유
    // 데이터를 불러올 때 movie를 불러오지 않고 사용할 때 불러옴
    // 외래키의 이름은 movie_mno로 만들어짐
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
