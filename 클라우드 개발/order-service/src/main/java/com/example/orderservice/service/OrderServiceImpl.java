package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.entity.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    @Override
    public OrderDto createOrder(OrderDto orderDetails) {
        orderDetails.setOrderId(UUID.randomUUID().toString());
        orderDetails.setTotalPrice(orderDetails.getQty()*orderDetails.getUnitPrice());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = mapper.map(orderDetails, OrderEntity.class);
        log.info(orderDetails.toString());
        orderRepository.save(orderEntity);

        return orderDetails;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity byOrderId = orderRepository.findByOrderId(orderId);
        OrderDto order = new ModelMapper().map(byOrderId, OrderDto.class);
        return order;
    }

    @Override
    public Iterable<OrderEntity> getOrderByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
