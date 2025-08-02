package dev.scastillo.inventory.unit.adapter.web.controller;

import dev.scastillo.inventory.adapter.web.controller.InventoryController;
import dev.scastillo.inventory.adapter.web.dto.ProductResponseDto;
import dev.scastillo.inventory.adapter.web.dto.UpdateStockRequestDto;
import dev.scastillo.inventory.adapter.web.mapper.ProductMapper;
import dev.scastillo.inventory.domain.service.ProductStockService;
import dev.scastillo.inventory.domain.service.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryControllerTest {
    private ProductStockService productStockService;
    private ProductMapper productMapper;
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        productStockService = mock(ProductStockService.class);
        productMapper = mock(ProductMapper.class);
        inventoryController = new InventoryController(productStockService, productMapper);
    }

    @Test
    void getProductById_ShouldReturnProductResponseDto_WhenProductExists() {
        Integer productId = 1;
        ProductResponse productResponse = ProductResponse.builder().id(productId).stock(10).build();
        ProductResponseDto productResponseDto = ProductResponseDto.builder().id(productId).stock(10).build();

        when(productStockService.getDetailProductById(productId)).thenReturn(productResponse);
        when(productMapper.toProductResponseDto(productResponse)).thenReturn(productResponseDto);

        ProductResponseDto result = inventoryController.getProductById(productId);

        assertEquals(productId, result.getId());
        assertEquals(10, result.getStock());
        verify(productStockService).getDetailProductById(productId);
        verify(productMapper).toProductResponseDto(productResponse);
    }

    @Test
    void getProductById_ShouldThrowException_WhenProductDoesNotExist() {
        Integer productId = 99;
        when(productStockService.getDetailProductById(productId)).thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Product not found"));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> inventoryController.getProductById(productId)
        );
        assertEquals(404, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Product not found"));
    }

    @Test
    void updateProductStock_ShouldReturnProductResponseDto_WhenUpdateIsSuccessful() {
        Integer productId = 1;
        Integer newQuantity = 15;
        UpdateStockRequestDto requestDto = UpdateStockRequestDto.builder().quantity(newQuantity).build();
        ProductResponse productResponse = ProductResponse.builder().id(productId).stock(newQuantity).build();
        ProductResponseDto productResponseDto = ProductResponseDto.builder().id(productId).stock(newQuantity).build();

        when(productStockService.updateProductStock(productId, newQuantity)).thenReturn(productResponse);
        when(productMapper.toProductResponseDto(productResponse)).thenReturn(productResponseDto);

        ProductResponseDto result = inventoryController.updateProductStock(productId, requestDto);

        assertEquals(productId, result.getId());
        assertEquals(newQuantity, result.getStock());
        verify(productStockService).updateProductStock(productId, newQuantity);
        verify(productMapper).toProductResponseDto(productResponse);
    }

    @Test
    void updateProductStock_ShouldThrowException_WhenProductDoesNotExist() {
        Integer productId = 99;
        UpdateStockRequestDto requestDto = UpdateStockRequestDto.builder().quantity(5).build();
        when(productStockService.updateProductStock(productId, 5))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Product not found"));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> inventoryController.updateProductStock(productId, requestDto)
        );
        assertEquals(404, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Product not found"));
    }
}
