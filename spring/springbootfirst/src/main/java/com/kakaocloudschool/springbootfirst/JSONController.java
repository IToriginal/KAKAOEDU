package com.kakaocloudschool.springbootfirst;

import com.kakaocloudschool.springbootfirst.dto.ParamDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@RestController
//공통된 URL
@RequestMapping("/api/v1/rest-api")
public class JSONController {
    private final Logger LOGGER =
            LoggerFactory.getLogger(JSONController.class);
    @RequestMapping(value="/hello",method= RequestMethod.GET)
    public String getHello(){
        LOGGER.info("Hello 요청이 왔습니다.");
        return "Get Hello";
    }
    //Spring 4.3 에서 추가된 요청 처리 어노테이션
    @GetMapping("/newhello")
    public String getnewHello(){
        return "Get New Hello";
    }
    //URL에 포함된 파라미터 처리
    @GetMapping("/product/{num}")
    public String getNum(@PathVariable("num") int num){
        return num + "";
    }

    //HttpSevletRequest를 이용해서 처리하기 위한 메서드를 구현
    @GetMapping("/param")
    public String getParam(HttpServletRequest request){
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String organization = request.getParameter("organization");
        return name + ":" + email + ":" + organization;
    }
    //@RequestParam 이용해서 처리하기 위한 메서드를 구현
    @GetMapping("/param1")
    public String getParam(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("organization") String organization){
        return name + ":" + email + ":" + organization;
    }

    //DTO 클래스를 생성해서 요청 처리하기 위한 메서드를 구현
    @GetMapping("/param2")
    public String getParam(
            ParamDTO paramDTO){
        return paramDTO.getName() + ":" +
                paramDTO.getEmail() + ":" +
                paramDTO.getOrganization();
    }
    //POST 방식의 요청 처리
    @PostMapping("/param")
    public String getPostParam(@RequestBody ParamDTO paramDTO){
        return paramDTO.toString();
    }
    //PUT 방식의 요청 처리
    @PutMapping("/param")
    public String getPutParam(@RequestBody ParamDTO paramDTO){
        return paramDTO.toString();
    }

    //JSON 문자열로 만들어서 출력
    @PutMapping("/param1")
    public ParamDTO getPutParam1(@RequestBody ParamDTO paramDTO){
        return paramDTO;
    }

    @PutMapping("/param2")
    public ResponseEntity<ParamDTO> getPutParam2(@RequestBody ParamDTO paramDTO){
        //상태 코드를 설정해서 결과를 리턴하는 것이 가능
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(paramDTO);
    }

    @DeleteMapping("/product/{num}")
    public String DeleteNum(@PathVariable("num") int num){
        return num + "";
    }

    @DeleteMapping("/product")
    public String DeleteParamNum(@PathParam("num") int num){
        return num + "";
    }

}
