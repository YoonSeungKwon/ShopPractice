package yoon.shop.test1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yoon.shop.test1.domain.Members;
import yoon.shop.test1.dto.MemberDto;
import yoon.shop.test1.message.Message;
import yoon.shop.test1.message.StatusEnum;
import yoon.shop.test1.service.MemberService;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //Search All Member
    @GetMapping("/member")
    public ResponseEntity<Message> memberSearch(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        List<Members> list = memberService.read();

        if(list == null){
            message.setStatusEnum(StatusEnum.NOT_FOUND);
            message.setMessage("등록된 사용자 없음");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.NOT_FOUND);
        }
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("전체 사용자");
        message.setData(list);
        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    //Search Specific Member
    @GetMapping("/member/{id}")
    public ResponseEntity<Message> memberSearch(@PathVariable String id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        MemberDto result = memberService.read(id);

        if(result == null){
            message.setStatusEnum(StatusEnum.NOT_FOUND);
            message.setMessage("사용자 없음");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.NOT_FOUND);
        }
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("idx: "+ id +" 사용자 정보");
        message.setData(result);
        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    //Register
    @PostMapping("/member/join")
    public ResponseEntity<Message> memberJoin(MemberDto dto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        MemberDto result = memberService.join(dto);

        if(result == null){
            message.setStatusEnum(StatusEnum.BAD_REQUEST);
            message.setMessage("회원 가입 실패");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.BAD_REQUEST);
        }
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage(result.getName() + "님 가입 완료");
        message.setData(result);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    //Login
    @PostMapping("/member/login")
    public ResponseEntity<Message> memberLogin(MemberDto dto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        MemberDto result = memberService.login(dto);

        if(result.getEmail().equals("EE")){
            message.setStatusEnum(StatusEnum.BAD_REQUEST);
            message.setMessage("사용자가 존재하지 않습니다.");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.BAD_REQUEST);
        }
        if(result.getEmail().equals("PE")){
            message.setStatusEnum(StatusEnum.BAD_REQUEST);
            message.setMessage("이메일 또는 비밀번호가 일치하지 않습니다.");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.BAD_REQUEST);
        }
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("안녕하세요   " + result.getName() + "님");
        message.setData(result);


        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    //Update Member
    @PutMapping("/member/link/{id}")
    public ResponseEntity<Message> memberUpdate(@PathVariable String id, MemberDto dto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        MemberDto result = memberService.update(id, dto);

        if(result.getEmail().equals("IE")){
            message.setStatusEnum(StatusEnum.BAD_REQUEST);
            message.setMessage("사용자를 찾을 수 없습니다.");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.BAD_REQUEST);
        }
        if(result.getEmail().equals("PE")){
            message.setStatusEnum(StatusEnum.BAD_REQUEST);
            message.setMessage("비밀번호가 일치하지 않습니다.");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.BAD_REQUEST);
        }
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("수정 완료 Email : "+result.getEmail()+" 이름 : "+result.getName() );
        message.setData(result);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    //Delete Member
    @DeleteMapping("/member/unlink/{id}")
    public ResponseEntity<Message> memberDelete(@PathVariable String id, String password){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        MemberDto result = memberService.delete(id, password);

        if(result.getEmail().equals("IE")){
            message.setStatusEnum(StatusEnum.BAD_REQUEST);
            message.setMessage("사용자를 찾을 수 없습니다.");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.BAD_REQUEST);
        }
        if(result.getEmail().equals("PE")){
            message.setStatusEnum(StatusEnum.BAD_REQUEST);
            message.setMessage("비밀번호가 일치하지 않습니다.");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.BAD_REQUEST);
        }
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("삭제 완료 Email : "+result.getEmail()+" 이름 : "+result.getName() );
        message.setData(result);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

}
