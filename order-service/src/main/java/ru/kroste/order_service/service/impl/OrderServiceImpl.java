package ru.kroste.order_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kroste.order_service.dto.CreateOrderRequest;
import ru.kroste.order_service.dto.OrderResponse;
import ru.kroste.order_service.repository.OrderRepository;
import ru.kroste.order_service.service.OrderService;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    public final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        return null;
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return null;
    }
}
