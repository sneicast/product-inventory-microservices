package dev.scastillo.inventory.infraestructure.repository;

import dev.scastillo.inventory.domain.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataProductStockRepository extends JpaRepository<ProductStock, Integer> {
    Optional<ProductStock> findByProductId(Integer productId);
}
