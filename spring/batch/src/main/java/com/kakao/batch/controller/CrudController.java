package com.kakao.batch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.batch.dto.MemberDTO;

@RestController
@RequestMapping("/api/v1/crud-api")
public class CrudController {

    @GetMapping
    public String getName() {
        return "itoriginal";
    }

    @GetMapping(value = "/{variable}")
    public String getValiable(@PathVariable String variable) {
        return variable;
    }

    //@RequestParam이 없어도 파라미터를 변수로 받을 수 있다.
    //변수가 여러 개이면 @RequestParam이 없으면 어떤 변수에 어떤 파라미터가 대입 될지 알 수 없음
    //@ReqeustParam(파라미터이름) 변수를 작성하면 파라미터 이름과 일치하는 파라미터를 찾아서 변수에 대입
    //DTO(Command Instance)로 여러 개를 한꺼번에 받을 수 있는데 이 경우도 파라미터 이름과 일치하는 속성을 찾아서 대입 시켜주는데
    //예전에는 이 방법을 잘 사용하지 않았는데 이유는 가독성이 떨어지기 때문
    //최근에는 Command Instance와 @RequestParam을 같이 설정
    @GetMapping(value = {"/param"})
    public String getParameter(@RequestParam("name") String name) {
        return "Hello" + name + "!!!";
    }

    //JSON 응답을 하고자 하는 경우에는 DTO나 Map 또는 Collection을 리턴하면 되는데 이 경우는 데이터만 전송이 된다.
    //ResponseEntity를 이용하게 되면 상태를 같이 전송할 수 있다.
    //모든 프로젝트를 혼자 개발하는 경우가 아니라면 Collection을 리턴하는 것은 하지 않는 것이 좋다.
    //최근에는 Map 보다는 DTO를 권장한다.
    //@RequestBody는 클라이언트가 데이터를 body에 포함시켜 전송한 경우 받는 방법이다.
    @PostMapping
    public ResponseEntity<MemberDTO> getMember(
        @RequestBody MemberDTO request,
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("organization") String organization) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName(name);
        memberDTO.setEmail(email);
        memberDTO.setOrganization(memberDTO.getOrganization());

        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }

    @PostMapping(value="/add-header")
    public ResponseEntity<MemberDTO> addHeader(
        @RequestHeader("my-header") String header,
        @RequestBody MemberDTO memberDTO) {
        System.out.println(header);
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }
}
