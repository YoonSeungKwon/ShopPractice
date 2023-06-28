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
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_idx")
    private long idx;

    @Column(nullable = false, length=250)
    private String name;

    @Column(nullable = false, length=50)
    private String category;

    @Column(nullable = false)
    private int price;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime regdate;
}
