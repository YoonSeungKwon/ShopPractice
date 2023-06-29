package yoon.shop.test1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoon.shop.test1.domain.Members;
import yoon.shop.test1.domain.Orders;
import yoon.shop.test1.domain.Product;
import yoon.shop.test1.dto.OrderDto;
import yoon.shop.test1.repository.MemberRepository;
import yoon.shop.test1.repository.OrderRepository;
import yoon.shop.test1.repository.ProductRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public List<OrderDto> read(){
        List<Orders> orderList = orderRepository.findAll();
        List<OrderDto> list = new ArrayList<>();
        for(Orders o:orderList){
            list.add(o.orderToDto());
        }
        return list;
    }

    public OrderDto read(String orderIdx) throws Exception{
        Long idx = Long.parseLong(orderIdx);
        Orders order = orderRepository.findOrdersByIdx(idx);

        if(order == null)
            return new OrderDto(0, "None", null, null, null);

        OrderDto dto = order.orderToDto();

        return dto;
    }

    public OrderDto join(String productIdx, OrderDto dto, Principal principal)throws Exception{
        Long idx = Long.parseLong(productIdx);
        Product product = productRepository.findProductByIdx(idx);
        Members member = memberRepository.findMembersByEmail(principal.getName());
        if(product == null || member == null)
            return new OrderDto(0, "None", null, null, null);
        if(dto.getNumber() <= 0 || dto.getAddress() == null)
            return new OrderDto(0, "Not", null, null, null);

        Orders order = Orders.builder()
                .number(dto.getNumber())
                .address(dto.getAddress())
                .price(dto.getNumber()*product.getPrice())
                .buyer(member)
                .product(product)
                .build();
        orderRepository.save(order);

        return new OrderDto(order.getNumber(), order.getAddress(), member.getName(), product.getName(), order.getPrice());
    }

    public OrderDto update(String orderIdx, OrderDto dto, Principal principal)throws Exception{
        Long idx = Long.parseLong(orderIdx);
        Orders order = orderRepository.findOrdersByIdx(idx);
        Members member = memberRepository.findMembersByEmail(principal.getName());
        if(order == null || member == null)
            return new OrderDto(0, "None", null, null, null);
        if(dto.getNumber() <= 0 || dto.getAddress() == null)
            return new OrderDto(0, "Not", null, null, null);
        order.setPrice((order.getPrice()/ order.getNumber())* dto.getNumber());
        order.setNumber(dto.getNumber());
        order.setAddress(dto.getAddress());
        orderRepository.save(order);

        return new OrderDto(order.getNumber(), order.getAddress(), order.getBuyer().getName(), order.getProduct().getName(), order.getPrice());
    }

    public OrderDto delete(String orderIdx, Principal principal)throws Exception{
        Long idx = Long.parseLong(orderIdx);
        Orders order = orderRepository.findOrdersByIdx(idx);
        Members member = memberRepository.findMembersByEmail(principal.getName());
        if(order == null || member ==  null)
            return new OrderDto(0, "None", null, null, null);
        if(order.getBuyer() != member)
            return new OrderDto(0, "Not", null, null, null);

        OrderDto dto = new OrderDto(order.getNumber(), order.getAddress(), order.getBuyer().getName(), order.getProduct().getName(), order.getPrice());
        orderRepository.delete(order);

        return dto;
    }


}
