package dev.scastillo.product.domain.repository;

import dev.scastillo.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Integer id);
    List<Product> findAll();
}
