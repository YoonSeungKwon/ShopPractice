package yoon.shop.test1.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_member")
    private Members member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_product")
    private Product product;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime regdate;

}
