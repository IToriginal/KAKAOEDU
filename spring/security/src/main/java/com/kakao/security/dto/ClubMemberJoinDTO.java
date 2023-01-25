package com.kakao.security.dto;

import lombok.Data;

@Data
public class ClubMemberJoinDTO {
    private String mid;
    private String mpw;
    private String name;
    private String email;
    private boolean del;
    private boolean social;
}
