package dev.scastillo.product.unit.adapter.web.controller;

import dev.scastillo.product.adapter.web.controller.ProductController;
import dev.scastillo.product.adapter.web.dto.ProductCreateRequestDto;
import dev.scastillo.product.adapter.web.dto.ProductDto;
import dev.scastillo.product.adapter.web.mapper.ProductMapper;
import dev.scastillo.product.domain.model.Product;
import dev.scastillo.product.domain.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductControllerTest {
    private ProductService productService;
    private ProductMapper productMapper;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productMapper = mock(ProductMapper.class);
        productController = new ProductController(productService, productMapper);
    }

    @Test
    void createProduct_ShouldReturnProductDto_WhenProductIsCreated() {
        // Arrange
        ProductCreateRequestDto requestDto = new ProductCreateRequestDto();
        Product productDomain = new Product();
        Product savedProduct = new Product();
        ProductDto productDto = new ProductDto();

        when(productMapper.toDomain(requestDto)).thenReturn(productDomain);
        when(productService.createProduct(productDomain)).thenReturn(savedProduct);
        when(productMapper.toDto(savedProduct)).thenReturn(productDto);

        // Act
        ProductDto result = productController.createProduct(requestDto);

        // Assert
        assertEquals(productDto, result);
        verify(productMapper).toDomain(requestDto);
        verify(productService).createProduct(productDomain);
        verify(productMapper).toDto(savedProduct);
    }

    @Test
    void getProductById_ShouldReturnProductDto_WhenProductExists() {
        // Arrange
        int productId = 1;
        Product productDomain = new Product();
        ProductDto productDto = new ProductDto();

        when(productService.getProductById(productId)).thenReturn(productDomain);
        when(productMapper.toDto(productDomain)).thenReturn(productDto);

        // Act
        ProductDto result = productController.getProductById(productId);

        // Assert
        assertEquals(productDto, result);
        verify(productService).getProductById(productId);
        verify(productMapper).toDto(productDomain);
    }

    @Test
    void getProductById_ShouldThrowException_WhenProductDoesNotExist() {
        // Arrange
        int productId = 99;
        when(productService.getProductById(productId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId));

        // Act & Assert
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> productController.getProductById(productId)
        );
        assertEquals("404 NOT_FOUND \"Product not found with id: 99\"", exception.getMessage());
        verify(productService).getProductById(productId);
        verify(productMapper, never()).toDto(any());
    }

    void getAllProducts_ShouldReturnProductDtoList_WhenProductsExist() {
        // Arrange
        Product product1 = new Product();
        Product product2 = new Product();
        ProductDto dto1 = new ProductDto();
        ProductDto dto2 = new ProductDto();

        when(productService.getAllProducts()).thenReturn(List.of(product1, product2));
        when(productMapper.toDto(product1)).thenReturn(dto1);
        when(productMapper.toDto(product2)).thenReturn(dto2);

        // Act
        List<ProductDto> result = productController.getAllProducts();

        // Assert
        assertEquals(List.of(dto1, dto2), result);
        verify(productService).getAllProducts();
        verify(productMapper).toDto(product1);
        verify(productMapper).toDto(product2);
    }

    @Test
    void getAllProducts_ShouldReturnEmptyList_WhenNoProductsExist() {
        // Arrange
        when(productService.getAllProducts()).thenReturn(List.of());

        // Act
        List<ProductDto> result = productController.getAllProducts();

        // Assert
        assertEquals(List.of(), result);
        verify(productService).getAllProducts();
        verify(productMapper, never()).toDto(any());
    }

}
