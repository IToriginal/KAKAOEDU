package com.example.board.service;

import com.example.board.domain.Reply;
import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResponseDTO;
import com.example.board.dto.ReplyDTO;
import com.example.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private BoardService boardService;

    //등록 테스트
    @Test
    public void registerTest(){
        BoardDTO dto = BoardDTO.builder()
                .title("등록 테스트")
                .content("등록을 테스트 합니다")
                .writerEmail("user85@kakao.com")
                .build();
        Long bno = boardService.register(dto);
        System.out.println(bno);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResponseDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
        System.out.println(result);
    }

    @Test
    public void testGet(){
        Long bno = 100L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }

    @Test
    public void testDelete(){
        boardService.removeWithReplies(100L);
    }

    @Test
    public void testUpdate(){
        BoardDTO dto = BoardDTO.builder()
                .bno(1000L)
                .title("제목 변경")
                .content("내용 변경")
                .build();
        System.out.println(boardService.modify(dto));
    }

    @Autowired
    ReplyService replyService;

    @Test
    public void testGetList(){
        //게시글 번호를 이용해서 댓글 가져오기
        //DB에 저장된 샘플데이터 확인 (reply -> board_bno : 76 사용)
        List<ReplyDTO> list = replyService.getList(76L);
        list.forEach(dto -> System.out.println(dto));
    }

    @Test
    public void insertReply(){
        ReplyDTO dto = ReplyDTO.builder()
                .text("댓글 삽입 테스트")
                .replyer("user1@kakao.com")
                .bno(76L)
                .build();
        System.out.println(replyService.register(dto));
    }
}
