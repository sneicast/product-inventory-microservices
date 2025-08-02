package dev.scastillo.product.domain.service;

import dev.scastillo.product.domain.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);
    Product createProduct(Product product);
}
