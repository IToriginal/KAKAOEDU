package com.kakao.guestbook.controller;

import com.kakao.guestbook.domain.GuestBook;
import com.kakao.guestbook.dto.GuestBookDTO;
import com.kakao.guestbook.dto.PageRequestDTO;
import com.kakao.guestbook.dto.PageResponseDTO;
import com.kakao.guestbook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class JSONController {
    private final GuestBookService guestBookService;

    @GetMapping("/guestbook/list.json")
    public PageResponseDTO<GuestBookDTO, GuestBook> list(PageRequestDTO pageRequestDTO){
        return guestBookService.getList(pageRequestDTO);
    }
}
