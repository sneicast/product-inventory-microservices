package dev.scastillo.inventory.domain.service;

import dev.scastillo.inventory.domain.service.dto.ProductResponse;

public interface ProductStockService {
    ProductResponse getDetailProductById(Integer productId);
    ProductResponse updateProductStock(Integer productId, Integer stock);
}
