package com.mukesh.micro.order_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    @GetExchange("/api/inventory/in-stock")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);

    // Fallback method when the circuit breaker is triggered
    default boolean fallbackMethod(String skuCode, Integer quantity, Throwable throwable) {
        log.warn("Fallback triggered for SKU: {} and quantity: {}. Reason: {}", skuCode, quantity, throwable.getMessage());
        return false; // Return false indicating that the product is not in stock
    }
}
