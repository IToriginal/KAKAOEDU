package com.kakao.review.service;

import com.kakao.review.domain.Member;
import com.kakao.review.domain.Movie;
import com.kakao.review.domain.Review;
import com.kakao.review.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getList(Long mno); //영화에 해당하는 리뷰를 가져오기
    Long register(ReviewDTO reviewDTO); //리뷰 등록
    Long modify(ReviewDTO reviewDTO); //리뷰 수정
    Long remove(Long rnum); //리뷰 삭제

    //DTO를 ENTITY로 변환해주는 메서드
    default Review dtoToEntity(ReviewDTO reviewDTO){
        Review review = Review.builder()
                .reviewnum(reviewDTO.getReviewNum())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .movie(Movie.builder()
                        .mno(reviewDTO.getMno())
                        .build())
                .member(Member.builder()
                        .mid(reviewDTO.getMid())
                        .build())
                .build();

        return review;
    }

    //ENTITY를 DTO로 변환해주는 메서드
    default ReviewDTO entityToDto(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewNum(review.getReviewnum())
                .mno(review.getMovie().getMno())
                .mid(review.getMember().getMid())
                .email(review.getMember().getEmail())
                .nickname(review.getMember().getNickname())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();
        return reviewDTO;
    }

}
