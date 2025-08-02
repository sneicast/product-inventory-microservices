package dev.scastillo.product.unit.application.service;

import dev.scastillo.product.application.service.ProductServiceImpl;
import dev.scastillo.product.domain.model.Product;
import dev.scastillo.product.domain.repository.ProductRepository;
import dev.scastillo.product.shared.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {
    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp(){
        this.productRepository = mock(ProductRepository.class);
        this.productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getAllProducts_ShouldReturnProductList_WhenProductsExist() {
        // Arrange
        Product product1 = Product.builder()
                .id(1)
                .name("Producto 1")
                .price(new BigDecimal("10.00"))
                .description("Descripción del producto 1")
                .build();
        Product product2 = Product.builder()
                .id(2)
                .name("Producto 2")
                .price(new BigDecimal("20.00"))
                .description("Descripción del producto 2")
                .build();
        List<Product> products = List.of(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertEquals(products, result);
        verify(productRepository).findAll();
    }

    @Test
    void getAllProducts_ShouldReturnEmptyList_WhenNoProductsExist() {
        // Arrange
        when(productRepository.findAll()).thenReturn(List.of());

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertEquals(List.of(), result);
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenExists() {
        // Arrange
        Product product = Product.builder()
                .id(1)
                .name("Producto Test")
                .price(new BigDecimal("99.99"))
                .description("Descripción de prueba")
                .build();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.getProductById(1);

        // Assert
        assertEquals(product, result);
        verify(productRepository).findById(1);
    }

    @Test
    void getProductById_ShouldThrowException_WhenNotFound() {
        String expectedMessage = "No fue encontrado el producto con id: 2";
        // Arrange
        when(productRepository.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> productService.getProductById(2)
        );
        assertTrue(exception.getMessage().contains(expectedMessage));
        verify(productRepository).findById(2);
    }

    @Test
    void createProduct_ShouldSaveAndReturnProduct() {
        // Arrange
        Product product = Product.builder()
                .id(1)
                .name("Producto Nuevo")
                .price(new BigDecimal("50.00"))
                .description("Producto de prueba")
                .build();
        when(productRepository.save(product)).thenReturn(product);

        // Act
        Product result = productService.createProduct(product);

        // Assert
        assertEquals(product, result);
        verify(productRepository).save(product);
    }
}
