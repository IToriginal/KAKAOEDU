package com.kakao.review.repository;

import com.kakao.review.domain.Member;
import com.kakao.review.domain.Movie;
import com.kakao.review.domain.MovieImage;
import com.kakao.review.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTests {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieImageRepository movieImageRepository;

    @Test
    public void insertMovie(){
        //영화 100개 생성 후 삽입
        IntStream.rangeClosed(1, 100).forEach(i->{
            Movie movie = Movie.builder()
                    .title("Movie Sample Data...[" + i + "]")
                    .build();
            movieRepository.save(movie);
            int count = (int)(Math.random() * 5) + 1;
            for (int j=0; j<count; j++){
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("SampleImage" + j + ".png")
                        .build();
                movieImageRepository.save(movieImage);
            }
        });
    }

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMember(){
        IntStream.rangeClosed(1, 100).forEach(i->{
            Member member = Member.builder()
                    .email("sample" + i + "@sample.com")
                    .pw("sample" + i)
                    .nickname("reviewer" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReview(){
        IntStream.rangeClosed(1, 200).forEach(i->{
            //영화 번호
            Long mno =(long)(Math.random() * 100) + 1;
            //회원 번호
            Long mid =(long)(Math.random() * 100) + 1;

            Movie movie = Movie.builder().mno(mno).build();
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .movie(movie)
                    .member(member)
                    .grade((int)(Math.random()*5)+1)
                    .text("Sample Review...[" + i + "]")
                    .build();
            reviewRepository.save(review);
        });
    }

    @Test
    //JOIN 연습
    public void joinTest(){
        Pageable pageable = PageRequest.of(0,10, Sort.Direction.DESC, "mno");
        Page<Object[]> result = movieRepository.getList(pageable);
        for(Object[] objects : result.getContent()){
            System.out.println(Arrays.toString(objects));
        }
    }
}
