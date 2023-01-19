package com.kakao.review.service;

import com.kakao.review.domain.Employee;

public interface EmployeeService {
    Employee createEmployee(String empId, String fname, String sname);
}
