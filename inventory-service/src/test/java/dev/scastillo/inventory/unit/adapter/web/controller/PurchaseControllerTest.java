package dev.scastillo.inventory.unit.adapter.web.controller;

import dev.scastillo.inventory.adapter.web.controller.PurchaseController;
import dev.scastillo.inventory.adapter.web.dto.PurchaseCreateRequestDto;
import dev.scastillo.inventory.adapter.web.dto.PurchaseResponseDto;
import dev.scastillo.inventory.adapter.web.mapper.PurchaseMapper;
import dev.scastillo.inventory.domain.service.PurchaseService;
import dev.scastillo.inventory.domain.service.dto.PurchaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PurchaseControllerTest {
    private PurchaseService purchaseService;
    private PurchaseMapper purchaseMapper;
    private PurchaseController purchaseController;

    @BeforeEach
    public void setUp() {
        purchaseService =  mock(PurchaseService.class);
        purchaseMapper = mock(PurchaseMapper.class);
        purchaseController = new PurchaseController(purchaseService, purchaseMapper);
    }

    @Test
    void createPurchase_ShouldReturnPurchaseResponseDto_WhenCreationIsSuccessful() {
        PurchaseCreateRequestDto requestDto = PurchaseCreateRequestDto.builder()
                .productId(1)
                .quantity(2)
                .build();
        PurchaseResponse purchaseResponse = PurchaseResponse.builder()
                .id(10L)
                .productId(1)
                .quantity(2)
                .build();
        PurchaseResponseDto responseDto = PurchaseResponseDto.builder()
                .id(10L)
                .productId(1)
                .quantity(2)
                .build();

        when(purchaseService.createPurchase(1, 2)).thenReturn(purchaseResponse);
        when(purchaseMapper.toDto(purchaseResponse)).thenReturn(responseDto);

        PurchaseResponseDto result = purchaseController.createPurchase(requestDto);

        assertEquals(10L, result.getId());
        assertEquals(1, result.getProductId());
        assertEquals(2, result.getQuantity());
        verify(purchaseService).createPurchase(1, 2);
        verify(purchaseMapper).toDto(purchaseResponse);
    }

    @Test
    void createPurchase_ShouldThrowException_WhenServiceThrows() {
        PurchaseCreateRequestDto requestDto = PurchaseCreateRequestDto.builder()
                .productId(99)
                .quantity(5)
                .build();

        when(purchaseService.createPurchase(99, 5))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Error al crear compra"));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> purchaseController.createPurchase(requestDto)
        );
        assertEquals(400, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Error al crear compra"));
    }

    @Test
    void getPurchaseById_ShouldReturnPurchaseResponseDto_WhenPurchaseExists() {
        Integer purchaseId = 1;
        PurchaseResponse purchaseResponse = PurchaseResponse.builder()
                .id(1L)
                .productId(10)
                .quantity(3)
                .build();
        PurchaseResponseDto responseDto = PurchaseResponseDto.builder()
                .id(1L)
                .productId(10)
                .quantity(3)
                .build();

        when(purchaseService.getPurchaseById(1L)).thenReturn(purchaseResponse);
        when(purchaseMapper.toDto(purchaseResponse)).thenReturn(responseDto);

        PurchaseResponseDto result = purchaseController.getPurchaseById(purchaseId);

        assertEquals(1L, result.getId());
        assertEquals(10, result.getProductId());
        assertEquals(3, result.getQuantity());
        verify(purchaseService).getPurchaseById(1L);
        verify(purchaseMapper).toDto(purchaseResponse);
    }

    @Test
    void getPurchaseById_ShouldThrowException_WhenPurchaseDoesNotExist() {
        Integer purchaseId = 99;
        when(purchaseService.getPurchaseById(99L))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Purchase not found"));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> purchaseController.getPurchaseById(purchaseId)
        );
        assertEquals(404, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Purchase not found"));
    }

}
