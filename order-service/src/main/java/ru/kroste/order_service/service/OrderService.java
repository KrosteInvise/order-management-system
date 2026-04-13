package ru.kroste.order_service.service;

import ru.kroste.order_service.dto.CreateOrderRequest;
import ru.kroste.order_service.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrder(Long id);
}
