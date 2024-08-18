package com.mukesh.micro.product.dto;

import lombok.Data;

import java.math.BigDecimal;

public record ProductRequest(String id, String name, String description, BigDecimal price) {
}
