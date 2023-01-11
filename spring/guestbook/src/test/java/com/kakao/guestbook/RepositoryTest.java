package com.kakao.guestbook;

import com.kakao.guestbook.domain.GuestBook;
import com.kakao.guestbook.domain.QGuestBook;
import com.kakao.guestbook.persistence.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    //데이터 삽입 메서드 테스트
    public void insertDummies(){
        IntStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .title("제목..." + i)
                    .content("내용..." + i)
                    .writer("user" + (i%10))
                    .build();
            System.out.println(guestBookRepository.save(guestBook));
        });
    }

    @Test
    //데이터 수정 메서드 테스트
    public void updateDummies(){
        GuestBook guestBook = GuestBook.builder()
                .gno(1L)
                .title("Update This Title")
                .content("Update This Content")
                .writer("IToriginal")
                .build();
        System.out.println(guestBookRepository.save(guestBook));
    }

    @Test
    //title에 1이라는 글자가 포함된 Entity 조회
    public void testQuery1(){
        //10개씩 첫번째 페이지의 데이터를 조회
        //modDate의 내림차순 정렬
        Pageable pageable = (Pageable) PageRequest.of(
                0, 10,
                Sort.by("modDate").descending());
        //querydsl 수행
        QGuestBook qGuestBook = QGuestBook.guestBook;

        //title에 1이 포함된 조건을 생성
        String keyword ="1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        builder.and(expression);

        Page<GuestBook> result =
                guestBookRepository.findAll(builder, pageable);
        for(GuestBook guestBook : result.getContent()){
            System.out.println(guestBook);
        }
    }

    //title 또는 content에 1이 포함된 데이터를 조회
    @Test
    public void testQuery2(){
        //10개씩 첫번째 페이지의 데이터를 조회
        //modDate의 내림차순 정렬
        Pageable pageable = (Pageable) PageRequest.of(0, 10, Sort.by("modDate").descending());

        //querydsl 수행을 위해서 Q 클래스 가져오기
        QGuestBook qGuestBook = QGuestBook.guestBook;

        String keyword ="1";
        BooleanBuilder builder = new BooleanBuilder();

        //title에 포함된 조건
        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        //content에 포함된 조건
        BooleanExpression exContent = qGuestBook.content.contains(keyword);
        //2개의 조건을 or로 결합
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        //조건 추가 (gno가 100보다 작음)
        builder.and(qGuestBook.gno.lt(100L));

        Page<GuestBook> result = guestBookRepository.findAll(builder,pageable);
        for (GuestBook guestBook : result.getContent()){
            System.out.println(guestBook);
        }
    }
}
