package dev.scastillo.inventory.adapter.web.controller;

import dev.scastillo.inventory.adapter.web.dto.PurchaseCreateRequestDto;
import dev.scastillo.inventory.adapter.web.dto.PurchaseResponseDto;
import dev.scastillo.inventory.adapter.web.mapper.PurchaseMapper;
import dev.scastillo.inventory.domain.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchases")
@AllArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;

    @PostMapping
    public PurchaseResponseDto createPurchase(@RequestBody PurchaseCreateRequestDto request){
        return purchaseMapper.toDto(purchaseService.createPurchase(request.getProductId(), request.getQuantity()));
    }
    @GetMapping("/{id}")
    public PurchaseResponseDto getPurchaseById(@PathVariable Integer id) {
        return purchaseMapper.toDto(purchaseService.getPurchaseById(id.longValue()));
    }
}
