package yoon.shop.test1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yoon.shop.test1.dto.OrderDto;
import yoon.shop.test1.message.Message;
import yoon.shop.test1.message.StatusEnum;
import yoon.shop.test1.service.OrderService;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order")   //See All Orders
    public ResponseEntity<Message> order(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        List<OrderDto> result = orderService.read();

        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("전체 주문 내역");
        message.setData(result);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/order/{orderIdx}")  //See Specific Order
    public ResponseEntity<Message> order(@PathVariable String orderIdx) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        OrderDto result = orderService.read(orderIdx);

        if(result.getAddress().equals("None")){
            message.setStatusEnum(StatusEnum.NOT_FOUND);
            message.setMessage("해당 주문내역이 존재하지 않음");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        }
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("주문 내역");
        message.setData(result);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/order/join/{productIdx}") //Make Order
    public ResponseEntity<Message> order(@PathVariable String productIdx, OrderDto dto, Principal principal) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        OrderDto result = orderService.join(productIdx,dto,principal);

        if(result.getAddress().equals("None")){
            message.setStatusEnum(StatusEnum.OK);
            message.setMessage("사용자 또는 물품이 존재하지 않습니다.");
            message.setData(result);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        }
        if(result.getAddress().equals("Not")){
            message.setStatusEnum(StatusEnum.OK);
            message.setMessage("유효하지 않은 요청입니다.");
            message.setData(result);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        }

        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("주문 내역");
        message.setData(result);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("/order/link/{orderIdx}")    //Change Address or Number
    public ResponseEntity<Message> updateOrder(@PathVariable String orderIdx, OrderDto dto, Principal principal) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        OrderDto result = orderService.update(orderIdx,dto,principal);

        if(result.getAddress().equals("None")){
            message.setStatusEnum(StatusEnum.OK);
            message.setMessage("사용자 또는 물품이 존재하지 않습니다.");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        }
        if(result.getAddress().equals("Not")){
            message.setStatusEnum(StatusEnum.OK);
            message.setMessage("유효하지 않은 요청입니다.");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        }

        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("주문 수정 내역");
        message.setData(result);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/order/unlink/{orderIdx}")   //Delete Order
    public ResponseEntity<Message> deleteOrder(@PathVariable String orderIdx, Principal principal) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        OrderDto result = orderService.delete(orderIdx,principal);

        if(result.getAddress().equals("None")){
            message.setStatusEnum(StatusEnum.OK);
            message.setMessage("사용자 또는 주문내역이 존재하지 않습니다.");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        }
        if(result.getAddress().equals("Not")){
            message.setStatusEnum(StatusEnum.OK);
            message.setMessage("주문자와 사용자가 일치하지 않습니다.");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        }

        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("주문 내역 삭제");
        message.setData(result);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

}
