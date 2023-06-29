package yoon.shop.test1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDto {

    private int number;

    private String address;

    private String buyer;

    private String product;

    private Integer price;

}
