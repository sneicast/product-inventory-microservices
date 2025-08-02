package dev.scastillo.inventory.unit.application.service;

import dev.scastillo.inventory.application.service.PurchaseServiceImpl;
import dev.scastillo.inventory.domain.model.ProductStock;
import dev.scastillo.inventory.domain.model.Purchase;
import dev.scastillo.inventory.domain.repository.ProductStockRepository;
import dev.scastillo.inventory.domain.repository.PurchaseRepository;
import dev.scastillo.inventory.domain.service.ProductServicePort;
import dev.scastillo.inventory.domain.service.dto.PurchaseResponse;
import dev.scastillo.inventory.infraestructure.rest.dto.ExternalProductDto;
import dev.scastillo.inventory.shared.exception.ConflictException;
import dev.scastillo.inventory.shared.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class PurchaseServiceImplTest {
    private PurchaseRepository purchaseRepository;
    private ProductServicePort productServicePort;
    private ProductStockRepository productStockRepository;
    private PurchaseServiceImpl purchaseService;

    @BeforeEach
    void setUp() {
        this.purchaseRepository = mock(PurchaseRepository.class);
        this.productServicePort = mock(ProductServicePort.class);
        this.productStockRepository = mock(ProductStockRepository.class);
        this.purchaseService = new PurchaseServiceImpl(purchaseRepository, productServicePort, productStockRepository);
    }

    @Test
    void createPurchase_ShouldSucceed_WhenStockIsSufficient() {
        Integer productId = 1;
        Integer quantity = 2;
        ExternalProductDto product = ExternalProductDto.builder()
                .id(productId)
                .name("Producto A")
                .price(BigDecimal.valueOf(100))
                .build();
        ProductStock productStock = ProductStock.builder()
                .productId(productId)
                .quantity(5)
                .build();
        Purchase purchase = Purchase.builder()
                .productId(productId)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .build();
        Purchase savedPurchase = Purchase.builder()
                .id(10L)
                .productId(productId)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .build();

        when(productServicePort.getProductById(productId)).thenReturn(Optional.of(product));
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(productStock));
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(savedPurchase);
        when(productStockRepository.save(any(ProductStock.class))).thenReturn(productStock);

        PurchaseResponse response = purchaseService.createPurchase(productId, quantity);

        assertEquals(savedPurchase.getId(), response.getId());
        assertEquals(productId, response.getProductId());
        assertEquals(quantity, response.getQuantity());
        assertEquals(product.getPrice(), response.getUnitPrice());
        assertEquals(product.getPrice().multiply(BigDecimal.valueOf(quantity)), response.getTotalPrice());
        verify(purchaseRepository).save(any(Purchase.class));
        verify(productStockRepository).save(any(ProductStock.class));
    }

    @Test
    void createPurchase_ShouldThrowException_WhenStockIsInsufficient() {
        Integer productId = 2;
        Integer quantity = 10;
        ExternalProductDto product = ExternalProductDto.builder()
                .id(productId)
                .name("Producto B")
                .price(BigDecimal.valueOf(50))
                .build();
        ProductStock productStock = ProductStock.builder()
                .productId(productId)
                .quantity(5)
                .build();

        when(productServicePort.getProductById(productId)).thenReturn(Optional.of(product));
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(productStock));

        String expectedMessage = "Stock insuficiente para el producto con id: " + productId;
        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> purchaseService.createPurchase(productId, quantity)
        );
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void createPurchase_ShouldThrowException_WhenProductDoesNotExist() {
        Integer productId = 3;
        Integer quantity = 1;
        when(productServicePort.getProductById(productId)).thenReturn(Optional.empty());
        String expectedMessage = "No fue encontrado el producto con id: " + productId;
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> purchaseService.createPurchase(productId, quantity)
        );
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void createPurchase_ShouldThrowException_WhenProductStockDoesNotExist() {
        Integer productId = 4;
        Integer quantity = 1;
        ExternalProductDto product = ExternalProductDto.builder()
                .id(productId)
                .name("Producto C")
                .price(BigDecimal.valueOf(30))
                .build();

        when(productServicePort.getProductById(productId)).thenReturn(Optional.of(product));
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.empty());

        String expectedMessage = "No fue encontrado el Stock del producto con id: " + productId;
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> purchaseService.createPurchase(productId, quantity)
        );
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void getPurchaseById_ShouldReturnResponse_WhenPurchaseExists() {
        Long purchaseId = 1L;
        Integer productId = 10;
        Purchase purchase = Purchase.builder()
                .id(purchaseId)
                .productId(productId)
                .quantity(3)
                .unitPrice(BigDecimal.valueOf(50))
                .totalPrice(BigDecimal.valueOf(150))
                .build();
        ExternalProductDto product = ExternalProductDto.builder()
                .id(productId)
                .name("Producto Test")
                .price(BigDecimal.valueOf(50))
                .build();

        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
        when(productServicePort.getProductById(productId)).thenReturn(Optional.of(product));

        PurchaseResponse response = purchaseService.getPurchaseById(purchaseId);

        assertEquals(purchaseId, response.getId());
        assertEquals(productId, response.getProductId());
        assertEquals("Producto Test", response.getProductName());
        assertEquals(purchase.getQuantity(), response.getQuantity());
        assertEquals(purchase.getUnitPrice(), response.getUnitPrice());
        assertEquals(purchase.getTotalPrice(), response.getTotalPrice());
    }

    @Test
    void getPurchaseById_ShouldThrowException_WhenPurchaseDoesNotExist() {
        Long purchaseId = 2L;
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.empty());

        String expectedMessage = "No fue encontrada la compra con Id: " + purchaseId;
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> purchaseService.getPurchaseById(purchaseId)
        );
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
