package com.kakao.review.service;

import com.kakao.review.domain.Movie;
import com.kakao.review.domain.MovieImage;
import com.kakao.review.dto.MovieDTO;
import com.kakao.review.dto.PageRequestDTO;
import com.kakao.review.dto.PageResponseDTO;
import com.kakao.review.repository.MovieImageRepository;
import com.kakao.review.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;

    @Override
    @Transactional
    public Long register(MovieDTO movieDTO) {
        log.warn("movieDTO : " + movieDTO);
        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        //영화 와 영화 이미지 정보 찾아오기
        Movie movie = (Movie)entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);
        movieImageList.forEach(movieImage -> {
            movieImageRepository.save(movieImage);
        });
        return movie.getMno();
    }

    @Override
    public PageResponseDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());
        // 데이터베이스에 요청
        Page<Object[]> result = movieRepository.getList(pageable);
        /**
         * Object 배열을 MovieDTO 타입으로 변경하기 위해 함수를 생성
         * 첫 번째 데이터 : Movie
         * 두 번째 데이터 : List<MovieImage>
         * 세 번째 데이터 : 평점의 평균
         * 네 번째 데이터 : 리뷰의 개수
         */
        Function<Object[], MovieDTO> fn =
                (arr -> entitiesToDTO( (Movie)arr[0],
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])), (Double) arr[2],
                (Long)arr[3])
        );
        return new PageResponseDTO<>(result, fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        Movie movie = (Movie) result.get(0)[0]; //Movie Entity는 가장 앞에 존재 - 모든 Row가 동일 값
        List<MovieImage> movieImageList = new ArrayList<>(); //MovieImage 개수 만큼 객체 필요
        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });
        double avg = (double) result.get(0)[2]; // 평균 점수 - 모든 Row가 동일
        Long reviewCnt = (Long) result.get(0)[3]; // 리뷰 개수 - 모든 Row가 동일한 값
        return entitiesToDTO(movie, movieImageList, avg, reviewCnt);
    }
}
