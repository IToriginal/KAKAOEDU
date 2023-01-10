package com.kakao.springbootjpa;

import com.kakao.springbootjpa.domain.Memo;
import com.kakao.springbootjpa.persistence.MemoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.annotation.Commit;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    MemoRepository memoRepository;

    //삽입 테스트
    @Test
    public void testInsert(){
        //데이터 100개 삽입
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder()
                    .memoText("Sample..." + i)
                    .build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testAll(){
        List<Memo> list = memoRepository.findAll();
        for(Memo memo : list){
            System.out.println(memo);
        }
    }

    @Test
    public void getById(){
        //기본키를 가지고 조회하면 없거나 1개의 데이터 리턴
        Optional<Memo> result = memoRepository.findById(100L);
        if(result.isPresent()){
            System.out.println(result.get());
        }else{
            System.out.println("데이터가 존재하지 않습니다.");
        }
    }

    @Test
    public void updateTest(){
        //기본값이 존재하면 수정이지만 존재하지 않으면 삽입
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("데이터 수정")
                .build();
        memoRepository.save(memo);

    }

    @Test
    public void deleteTest(){
        memoRepository.deleteById(100L); //기본키를 가지고 삭제
        memoRepository.delete(Memo.builder().mno(99L).build());
        //Entity를 이용해서 삭제

        //없는 데이터를 삭제하고자 하면 에러
        //삭제를 할 때는 존재 여부를 확인해야 합니다.
        memoRepository.deleteById(1000L);
    }

    @Test
    public void testPaging(){
        //10개씩 0페이지
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        //전체 페이지 개수
        System.out.println(result.getTotalPages());
        //조회된 데이터 순회
        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    //정렬해서 페이징
    @Test
    public void testSort(){
        //mno의 내림차순
        Sort sort = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Memo> result = memoRepository.findAll(pageable);
        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    @Test
    public void testSortConcate(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        //2개 결합 - sort1 의 값이 같으면 sort2로 정렬
        Sort sortAll = sort1.and(sort2);
        Pageable pageable = PageRequest.of(0, 10, sortAll);
        Page<Memo> result = memoRepository.findAll(pageable);
        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }

    }

    @Test
    public void queryMethod1(){
        List<Memo> list =
                memoRepository.findByMnoBetweenOrderByMnoDesc(
                        30L, 40L);
        for(Memo memo : list){
            System.out.println(memo);
        }
    }

    @Test
    public void queryMethod2(){
        Pageable pageable = PageRequest.of(1, 5);
        Page<Memo> result =
                memoRepository.findByMnoBetween(
                        0L, 50L, pageable);
        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    @Test
    //특정한 작업에서는 트랜잭션을 적용하지 않으면 예외가 발생
    @Transactional
    //트랜잭션이 적용되면 자동 Commit 되지 않으므로 Commit을 호출해야
    //실제 작업이 완성됩니다.
    //@Commit
    public void deleteMnoTest(){
        memoRepository.deleteByMnoLessThan(20L);

        List<Memo> list = memoRepository.findAll();
        for(Memo memo : list){
            System.out.println(memo);
        }
    }

    @Test
    public void testUpdateQuery(){
        System.out.println(
                memoRepository.updateMemoText(
                        11, "문자열"));
        System.out.println(
                memoRepository.updateMemoText(
                        Memo.builder().mno(12L).memoText("문자열")
                                .build()));
    }

    @Test
    public void testSelectQuery(){
        //mno의 내림차순으로 정렬해서 0번 페이지 10개의 데이터를 가져오는
        //Pageable 객체
        Pageable pageable = PageRequest.of(0, 10,
                Sort.by("mno").descending());
        Page<Memo> result = memoRepository.getListWithQuery(
                50L, pageable);
        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    @Test
    public void testObjectQuery(){
        //mno의 내림차순으로 정렬해서 0번 페이지 10개의 데이터를 가져오는
        //Pageable 객체
        Pageable pageable = PageRequest.of(0, 10,
                Sort.by("mno").descending());
        Page<Object []> result = memoRepository.getObjectWithQuery(
                50L, pageable);
        for(Object [] memo : result.getContent()){
            System.out.println(Arrays.toString(memo));
        }
    }

}
