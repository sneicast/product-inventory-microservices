package dev.scastillo.product.infraestructure.repository;

import dev.scastillo.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductRepository extends JpaRepository<Product, Integer> {
}
