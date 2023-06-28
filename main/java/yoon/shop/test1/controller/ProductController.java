package yoon.shop.test1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yoon.shop.test1.domain.Product;
import yoon.shop.test1.dto.MemberDto;
import yoon.shop.test1.dto.ProductDto;
import yoon.shop.test1.message.Message;
import yoon.shop.test1.message.StatusEnum;
import yoon.shop.test1.service.ProductService;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product") //See Products
    public ResponseEntity<Message> product(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        List<Product> list = productService.read();

        if(list == null){
            message.setStatusEnum(StatusEnum.NOT_FOUND);
            message.setMessage("상품 없음");
            message.setData(null);

            return new ResponseEntity<>(message, httpHeaders, HttpStatus.NOT_FOUND);
        }

        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("전체 상품 정보");
        message.setData(list);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/product/{idx}")    //See Specific Product
    public ResponseEntity<Message> product(@PathVariable String idx){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        ProductDto result = productService.read(idx);

        if(result.getName().equals("None")){
            message.setStatusEnum(StatusEnum.NOT_FOUND);
            message.setMessage("해당 상품이 존재하지 않음");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.NOT_FOUND);
        }
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("전체 상품 정보");
        message.setData(result);
        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/product/detail/{idx}")    //Product Detail Page
    public ResponseEntity<Message> detailProduct(@PathVariable String idx, Principal principal){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        ProductDto result = productService.read(idx, principal);

        if(result.getName().equals("None")){
            message.setStatusEnum(StatusEnum.NOT_FOUND);
            message.setMessage("해당 상품이 존재하지 않음");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.NOT_FOUND);
        }

        if (result.getName().equals("Not")){
            message.setStatusEnum(StatusEnum.NOT_FOUND);
            message.setMessage("판매자가 일치하지 않음");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.NOT_FOUND);
        }
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage(principal.getName() + "상품 정보");
        message.setData(result);
        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/product/join")   //Register Product
    public ResponseEntity<Message> joinProduct(ProductDto dto, Principal principal)throws NullPointerException{
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        ProductDto result = productService.join(principal, dto);

        if(result.getName().equals("Not") || principal == null){
            message.setStatusEnum(StatusEnum.NOT_FOUND);
            message.setMessage("해당 유저가 존재하지 않음");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.NOT_FOUND);
        }
        if(result.getName().equals("None")){
            message.setStatusEnum(StatusEnum.NOT_FOUND);
            message.setMessage("상품의 값이 유효하지 않음");
            message.setData(null);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.NOT_FOUND);
        }
        message.setStatusEnum(StatusEnum.OK);
        message.setMessage("상품 등록 완료");
        message.setData(result);
        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("/product/detail/{idx}/link")  //Update Product
    public String updateProduct(@PathVariable String idx, ProductDto pDto){
        return "";
    }

    @DeleteMapping("/product/unlink/{idx}") //Delete Product
    public String deleteProduct(@PathVariable String idx, Principal principal){
        return "";
    }
}
