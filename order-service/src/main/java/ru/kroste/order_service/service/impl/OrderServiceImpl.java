package ru.kroste.order_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kroste.order_service.dto.CreateOrderRequest;
import ru.kroste.order_service.dto.OrderResponse;
import ru.kroste.order_service.model.Order;
import ru.kroste.order_service.model.OrderItem;
import ru.kroste.order_service.model.OrderStatus;
import ru.kroste.order_service.repository.OrderRepository;
import ru.kroste.order_service.service.OrderService;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    public final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .userId(request.getUserId())
                .status(OrderStatus.PENDING)
                .totalPrice(calculateTotal(request.getItems()))
                .items(mapItems(request.getItems()))
                .build();

        order.getItems().forEach(item -> item.setOrder(order));

        Order saved = orderRepository.save(order);
        log.info("Order created: {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        order.setStatus(status);
        orderRepository.save(order);
        log.info("Order {} status updated to {}", orderId, status);
    }

    @Override
    public BigDecimal calculateTotal(List<CreateOrderRequest.OrderItemRequest> items) {
        //TODO: later price will be taken from inventory service
        return items.stream()
                .map(i -> BigDecimal.valueOf(i.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<OrderItem> mapItems(List<CreateOrderRequest.OrderItemRequest> items) {
        return items.stream()
                .map(i -> OrderItem.builder()
                        .productId(i.getProductId())
                        .quantity(i.getQuantity())
                        .price(BigDecimal.ZERO) //TODO: change later as well
                        .build())
                .toList();
    }

    @Override
    public OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream()
                        .map(i -> OrderResponse.OrderItemResponse.builder()
                                .productId(i.getProductId())
                                .quantity(i.getQuantity())
                                .price(i.getPrice())
                                .build())
                        .toList())
                .build();
    }
}