package ru.kroste.order_service.dto;

import lombok.Builder;
import lombok.Data;
import ru.kroste.order_service.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;

    @Data
    @Builder
    public static class OrderItemResponse {
        private Long productId;
        private Integer quantity;
        private BigDecimal price;
    }
}