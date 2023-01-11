package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResponseDTO;

public interface BoardService {
    //게시글 등록 요청 메서드
    Long register(BoardDTO dto);

    //게시글 목록 보기
    PageResponseDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    //게시글 번호를 가지고 댓글을 삭제
    void removeWithReplies(Long bno);

    //게시글 상세 보기
    BoardDTO get(Long bno);

    //게시글 수정
    Long modify(BoardDTO dto);

    //DTO -> Entity 로 변환해주는 메서드
    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getTitle())
                .writer(member)
                .build();
        return board;
    }
    //Entity -> DTO 로 변환해주는 메서드
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getTitle())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
        return dto;
    }
}
