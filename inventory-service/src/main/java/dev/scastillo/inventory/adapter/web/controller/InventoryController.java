package dev.scastillo.inventory.adapter.web.controller;

import dev.scastillo.inventory.adapter.web.dto.ProductResponseDto;
import dev.scastillo.inventory.adapter.web.dto.UpdateStockRequestDto;
import dev.scastillo.inventory.adapter.web.mapper.ProductMapper;
import dev.scastillo.inventory.domain.service.ProductStockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@AllArgsConstructor
public class InventoryController {
    private final ProductStockService productStockService;
    private final ProductMapper productMapper;

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable Integer id) {
        return productMapper.toProductResponseDto(productStockService.getDetailProductById(id));
    }

    @PatchMapping("/products/{id}/stock")
    public ProductResponseDto updateProductStock(@PathVariable Integer id, @RequestBody UpdateStockRequestDto updateStockRequestDto) {
        return productMapper.toProductResponseDto(productStockService.updateProductStock(id, updateStockRequestDto.getQuantity()));
    }
}
