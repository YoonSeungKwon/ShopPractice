package yoon.shop.test1.controller;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yoon.shop.test1.message.Message;
import yoon.shop.test1.message.StatusEnum;

import java.nio.charset.Charset;
import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/")
    public String main(){

        return "main";

    }

    @GetMapping("/login/success")
    public ResponseEntity<Message> loginSuccess(Principal principal){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("authentication info");
        message.setData(auth);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }


    @GetMapping("/auth")
    public ResponseEntity<Message> infoAuth(Principal principal){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("authentication info");
        message.setData(auth);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }
}
