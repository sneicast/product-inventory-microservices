package dev.scastillo.product.application.service;

import dev.scastillo.product.domain.model.Product;
import dev.scastillo.product.domain.repository.ProductRepository;
import dev.scastillo.product.domain.service.ProductService;
import dev.scastillo.product.shared.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No fue encontrado el producto con id: " + id));
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
