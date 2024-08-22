package com.mukesh.micro.order_service.controller;


import com.mukesh.micro.order_service.dto.OrderRequest;
import com.mukesh.micro.order_service.model.Order;
import com.mukesh.micro.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest){
        Order order = orderService.placeOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

}
