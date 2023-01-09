package com.kakaocloudschool.springbootfirst.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParamDTO {
    private String name;
    private String email;
    private String organization;
}
