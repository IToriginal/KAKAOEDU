package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    //기본 요청 생성
    @GetMapping({"/","/board/list"})
    public String list(PageRequestDTO pageRequestDTO, Model model){
        log.info("기본 목록 보기 요청");
        model.addAttribute("result", boardService.getList(pageRequestDTO));
        return "/board/list";
    }

    //게시물 등록 화면으로 이동하는 요청 - Forwarding
    @GetMapping("/board/register")
    public void register(Model model){
        log.info("등록 화면으로 Forwardinf");

    }

    //게시물을 등록하는 요청 - Redirect
    //RedirectAttributes - 1회용 세션
    @PostMapping("/board/register")
    public String register(BoardDTO dto, RedirectAttributes rattr){
        //파라미터 확인
        log.info("dto : " + dto.toString());
        //데이터 삽입
        Long bno = boardService.register(dto);
        rattr.addFlashAttribute("msg", bno + "등록");
        return "redirect:/board/list";
    }

    //ModelAttribute 는 파라미터로 사용하면 넘겨받은 데이터를 결과에 그대로 전달할 목적으로 사용
    @GetMapping({"/board/read", "/board/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model domain){
        log.info("bno: " + bno);
        BoardDTO boardDTO = boardService.get(bno);
        log.info(boardDTO);
        domain.addAttribute("dto", boardDTO);
    }
    @PostMapping("/board/modify")
    public String modify(BoardDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){
        log.info("post modify.........................................");
        log.info("dto: " + dto);
        //수정
        boardService.modify(dto);
        //상세보기로 이동할 때 글 번호와 현재 페이지 번호를 전달
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        redirectAttributes.addAttribute("bno",dto.getBno());

        return "redirect:/board/read";
    }
    //삭제처리 메서드
    @PostMapping("/board/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes){
        log.info("bno: " + bno);
        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg", bno +" 번 삭제");
        return "redirect:/board/list";
    }

}

