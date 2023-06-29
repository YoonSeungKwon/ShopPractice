package yoon.shop.test1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yoon.shop.test1.domain.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Orders findOrdersByIdx(Long idx);
}
