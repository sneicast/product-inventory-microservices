package dev.scastillo.inventory.domain.service;


import dev.scastillo.inventory.domain.model.Purchase;
import dev.scastillo.inventory.domain.service.dto.PurchaseResponse;

public interface PurchaseService {
    PurchaseResponse createPurchase(Integer productId, Integer quantity);
    PurchaseResponse getPurchaseById(Long id);

}
