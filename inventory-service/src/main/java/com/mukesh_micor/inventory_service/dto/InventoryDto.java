package com.mukesh_micor.inventory_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDto {
    private String skuCode;
    private Integer quantity;
}
