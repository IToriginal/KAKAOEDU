package com.kakao.guestbook.persistence;

import com.kakao.guestbook.domain.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestBookRepository
        extends JpaRepository<GuestBook, Long>,
        QuerydslPredicateExecutor<GuestBook> {
}
