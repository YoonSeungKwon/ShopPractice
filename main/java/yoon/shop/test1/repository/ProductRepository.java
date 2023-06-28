package yoon.shop.test1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yoon.shop.test1.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findProductByIdx(Long idx);

}
