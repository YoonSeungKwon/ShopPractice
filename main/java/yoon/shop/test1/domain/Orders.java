package yoon.shop.test1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import yoon.shop.test1.dto.OrderDto;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_idx")
    private long idx;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, length=250)
    private String address;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_member")
    private Members buyer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_product")
    private Product product;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime regdate;

    public Orders(int number, String address, int price, Members buyer, Product product){
        this.number = number;
        this.address = address;
        this.price = price;
        this.buyer = buyer;
        this.product = product;
    }

    public OrderDto orderToDto(){
        OrderDto dto = new OrderDto(
                this.getNumber(),
                this.getAddress(),
                this.getBuyer().getName(),
                this.getProduct().getName(),
                this.getPrice()
        );
        return dto;
    }
}
