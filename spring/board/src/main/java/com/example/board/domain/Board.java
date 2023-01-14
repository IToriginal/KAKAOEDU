package com.example.board.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //toString을 만들 때 writer의 toString 호출을 하지 않겠다
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bno;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) //FK 설정
    //처음에는 가져오지 않고 사용을 할 때 가져옴
    private Member writer;

    public void changeTitle(String title){
        if(title == null || title.trim().length() == 0){
            this.title = "무제";
            return;
        }
        this.title = title;
    }
    //content를 수정하는 메서드
    public void changeContent(String content){
        this.content=content;
    }
}
