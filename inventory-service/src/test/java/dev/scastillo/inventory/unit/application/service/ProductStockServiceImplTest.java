package dev.scastillo.inventory.unit.application.service;

import dev.scastillo.inventory.application.service.ProductStockServiceImpl;
import dev.scastillo.inventory.domain.model.ProductStock;
import dev.scastillo.inventory.domain.repository.ProductStockRepository;
import dev.scastillo.inventory.domain.service.ProductServicePort;
import dev.scastillo.inventory.domain.service.dto.ProductResponse;
import dev.scastillo.inventory.infraestructure.rest.dto.ExternalProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductStockServiceImplTest {
    private ProductStockRepository productStockRepository;
    private ProductServicePort productServicePort;
    private ProductStockServiceImpl productStockService;
    @BeforeEach
    void setUp() {
        this.productStockRepository =  mock(ProductStockRepository.class);
        this.productServicePort = mock(ProductServicePort.class);
        this.productStockService = new ProductStockServiceImpl(productStockRepository, productServicePort);
    }

    @Test
    void getDetailProductById_ShouldReturnProductWithZeroStock_WhenNoProductStockExists() {
        Integer productId = 1;
        ExternalProductDto externalProduct = ExternalProductDto.builder()
                .id(productId)
                .name("Producto A")
                .description("Desc")
                .price(BigDecimal.valueOf(100))
                .build();

        when(productServicePort.getProductById(productId)).thenReturn(Optional.of(externalProduct));
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.empty());

        ProductResponse response = productStockService.getDetailProductById(productId);

        assertEquals(productId, response.getId());
        assertEquals("Producto A", response.getName());
        assertEquals(0, response.getStock());
    }

    @Test
    void getDetailProductById_ShouldReturnProductWithStock_WhenProductStockExists() {
        Integer productId = 2;
        ExternalProductDto externalProduct = ExternalProductDto.builder()
                .id(productId)
                .name("Producto B")
                .description("Desc B")
                .price(BigDecimal.valueOf(200))
                .build();

        ProductStock productStock = ProductStock.builder()
                .productId(productId)
                .quantity(15)
                .build();

        when(productServicePort.getProductById(productId)).thenReturn(Optional.of(externalProduct));
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(productStock));

        ProductResponse response = productStockService.getDetailProductById(productId);

        assertEquals(productId, response.getId());
        assertEquals("Producto B", response.getName());
        assertEquals(15, response.getStock());
    }

    @Test
    void getDetailProductById_ShouldThrowNotFound_WhenProductDoesNotExist() {
        Integer productId = 99;
        when(productServicePort.getProductById(productId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> productStockService.getDetailProductById(productId)
        );

        assertEquals(404, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Product not found with id: " + productId));
    }

    @Test
    void updateProductStock_ShouldUpdateExistingStock() {
        Integer productId = 1;
        Integer newStock = 20;
        ExternalProductDto externalProduct = ExternalProductDto.builder()
                .id(productId)
                .name("Producto A")
                .description("Desc")
                .price(BigDecimal.valueOf(100))
                .build();
        ProductStock existingStock = ProductStock.builder()
                .productId(productId)
                .quantity(5)
                .build();

        when(productServicePort.getProductById(productId)).thenReturn(Optional.of(externalProduct));
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(existingStock));
        when(productStockRepository.save(existingStock)).thenReturn(existingStock);

        ProductResponse response = productStockService.updateProductStock(productId, newStock);

        assertEquals(productId, response.getId());
        assertEquals(newStock, response.getStock());
        verify(productStockRepository).save(existingStock);
    }

    @Test
    void updateProductStock_ShouldCreateStockIfNotExists() {
        Integer productId = 2;
        Integer newStock = 10;
        ExternalProductDto externalProduct = ExternalProductDto.builder()
                .id(productId)
                .name("Producto B")
                .description("Desc B")
                .price(BigDecimal.valueOf(200))
                .build();

        when(productServicePort.getProductById(productId)).thenReturn(Optional.of(externalProduct));
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.empty());
        // Simula el guardado del nuevo stock
        when(productStockRepository.save(any(ProductStock.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = productStockService.updateProductStock(productId, newStock);

        assertEquals(productId, response.getId());
        assertEquals(newStock, response.getStock());
        verify(productStockRepository).save(any(ProductStock.class));
    }

    @Test
    void updateProductStock_ShouldThrowNotFound_WhenProductDoesNotExist() {
        Integer productId = 99;
        Integer newStock = 5;
        when(productServicePort.getProductById(productId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> productStockService.updateProductStock(productId, newStock)
        );

        assertEquals(404, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Product not found with id: " + productId));
    }

}
