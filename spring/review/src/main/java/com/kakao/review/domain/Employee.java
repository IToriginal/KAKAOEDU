package com.kakao.review.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Employee {
    private String empId;
    private String firstName;
    private String secondName;
}
