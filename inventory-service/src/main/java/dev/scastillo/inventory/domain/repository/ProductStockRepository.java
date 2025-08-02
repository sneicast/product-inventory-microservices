package dev.scastillo.inventory.domain.repository;

import dev.scastillo.inventory.domain.model.ProductStock;
import java.util.Optional;

public interface ProductStockRepository {
    ProductStock save(ProductStock productStock);
    Optional<ProductStock> findByProductId(Integer productId);
    void deleteAll();
}
