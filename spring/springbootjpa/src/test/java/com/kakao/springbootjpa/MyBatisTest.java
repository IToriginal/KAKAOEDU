package com.kakao.springbootjpa;

import com.kakao.springbootjpa.mapper.MemoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisTest {
    @Autowired
    MemoMapper memoMapper;

    @Test
    public void testMyBatis(){
        System.out.println(memoMapper);
        System.out.println(memoMapper.listMemo());
    }
}
