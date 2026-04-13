package ru.kroste.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kroste.order_service.model.Order;
import ru.kroste.order_service.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    @Query("""
        SELECT o FROM Order o
        WHERE o.status = :status
        AND o.createdAt < :cutoff
    """)
    List<Order> findStaleOrders(
            @Param("status") OrderStatus status,
            @Param("cutoff") LocalDateTime cutoff
    );
}
