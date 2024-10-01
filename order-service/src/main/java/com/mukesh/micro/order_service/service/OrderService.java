package com.mukesh.micro.order_service.service;

import com.mukesh.micro.order_service.client.InventoryClient;
import com.mukesh.micro.order_service.dto.OrderRequest;
import com.mukesh.micro.order_service.model.Order;
import com.mukesh.micro.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        log.info("Attempting to place an order for SKU: {}, Quantity: {}", orderRequest.skuCode(), orderRequest.quantity());

        // Check if the product is in stock
        if (isProductInStock(orderRequest.skuCode(), orderRequest.quantity())) {
            Order order = buildOrder(orderRequest);

            log.info("Order for SKU: {} is in stock. Saving order with Order Number: {}", orderRequest.skuCode(), order.getOrderNumber());
            Order savedOrder = orderRepository.save(order);

            log.info("Order placed successfully with Order Number: {}", savedOrder.getOrderNumber());
            return savedOrder;
        } else {
            String errorMessage = "Product with SKU: " + orderRequest.skuCode() + " is not in stock";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    private boolean isProductInStock(String skuCode, int quantity) {
        log.debug("Checking stock for SKU: {}, Quantity: {}", skuCode, quantity);
        return inventoryClient.isInStock(skuCode, quantity);
    }

    private Order buildOrder(OrderRequest orderRequest) {
        log.debug("Building order for SKU: {}, Order Number: {}", orderRequest.skuCode(), orderRequest.orderNumber());

        Order order = new Order();

        // Check if orderRequest.orderNumber() is null or empty, and set it appropriately
        String orderNumber = (orderRequest.orderNumber() != null && !orderRequest.orderNumber().isEmpty())
                ? orderRequest.orderNumber()
                : UUID.randomUUID().toString();

        order.setOrderNumber(orderNumber); // Set the order number, either from request or generated

        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());

        return order;
    }

}
