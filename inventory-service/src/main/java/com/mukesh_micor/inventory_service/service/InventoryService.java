package com.mukesh_micor.inventory_service.service;

import com.mukesh_micor.inventory_service.dto.InventoryDto;
import com.mukesh_micor.inventory_service.model.Inventory;
import com.mukesh_micor.inventory_service.repositiory.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity){
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

    public Inventory createInventory(InventoryDto inventoryDto) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(inventoryDto.getSkuCode());
        inventory.setQuantity(inventoryDto.getQuantity());
        return inventoryRepository.save(inventory);
    }


}
