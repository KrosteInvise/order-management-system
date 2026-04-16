package ru.kroste.order_service.service;

import ru.kroste.order_service.dto.CreateOrderRequest;
import ru.kroste.order_service.dto.OrderResponse;
import ru.kroste.order_service.model.Order;
import ru.kroste.order_service.model.OrderItem;
import ru.kroste.order_service.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrder(Long id);
    List<OrderResponse> getOrdersByUser(Long userId);
    void updateStatus(Long orderId, OrderStatus status);
    BigDecimal calculateTotal(List<CreateOrderRequest.OrderItemRequest> items);
    List<OrderItem> mapItems(List<CreateOrderRequest.OrderItemRequest> items);
    OrderResponse mapToResponse(Order order);
}
