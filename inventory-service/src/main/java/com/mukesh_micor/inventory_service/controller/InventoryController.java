package com.mukesh_micor.inventory_service.controller;

import com.mukesh_micor.inventory_service.dto.InventoryDto;
import com.mukesh_micor.inventory_service.model.Inventory;
import com.mukesh_micor.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/in-stock")
    public ResponseEntity<Boolean> isInStock(
            @RequestParam String skuCode,
            @RequestParam Integer quantity) {

        boolean isInStock = inventoryService.isInStock(skuCode, quantity);
        return ResponseEntity.ok(isInStock);
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryDto inventoryDto) {
        Inventory createdInventory = inventoryService.createInventory(inventoryDto);
        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }
}
