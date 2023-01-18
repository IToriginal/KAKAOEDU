package com.kakao.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private double avg; //영화의 평균 평점
    private Long reviewCnt; //리뷰 수 jpa의 count()
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Long mno;
    private String title;

    //builder() 라는 메서드를 이용해서 생성할 때 기본으로 사용
    @Builder.Default
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();
}
