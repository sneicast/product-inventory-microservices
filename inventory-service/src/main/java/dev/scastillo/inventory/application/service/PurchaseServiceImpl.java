package dev.scastillo.inventory.application.service;

import dev.scastillo.inventory.domain.model.ProductStock;
import dev.scastillo.inventory.domain.model.Purchase;
import dev.scastillo.inventory.domain.repository.ProductStockRepository;
import dev.scastillo.inventory.domain.repository.PurchaseRepository;
import dev.scastillo.inventory.domain.service.ProductServicePort;
import dev.scastillo.inventory.domain.service.PurchaseService;
import dev.scastillo.inventory.domain.service.dto.PurchaseResponse;
import dev.scastillo.inventory.infraestructure.rest.dto.ExternalProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductServicePort productServicePort;
    private final ProductStockRepository productStockRepository;

    @Override
    public PurchaseResponse createPurchase(Integer productId, Integer quantity) {
        ExternalProductDto product = getProductById(productId);
        ProductStock productStock = getProductStockByProductId(productId);

        if (productStock.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product with id: " + productId);
        }

        Purchase purchase = Purchase.builder()
                .productId(productId)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .build();

        Purchase savedPurchase = purchaseRepository.save(purchase);

        productStock.setQuantity(productStock.getQuantity() - quantity);
        productStockRepository.save(productStock);

        return buildPurchaseResponse(savedPurchase, product.getName());
    }

    @Override
    public PurchaseResponse getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .map(purchase -> {
                    var product = getProductById(purchase.getProductId());
                    return buildPurchaseResponse(purchase, product.getName());
                })
                .orElseThrow(() -> new RuntimeException("Purchase not found with id: " + id));
    }


    private ExternalProductDto getProductById(Integer productId) {
        return productServicePort.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    private ProductStock getProductStockByProductId(Integer productId) {
        return productStockRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product stock not found with id: " + productId));
    }

    private PurchaseResponse buildPurchaseResponse(Purchase purchase, String productName) {
        return PurchaseResponse.builder()
                .id(purchase.getId())
                .productId(purchase.getProductId())
                .productName(productName)
                .quantity(purchase.getQuantity())
                .unitPrice(purchase.getUnitPrice())
                .totalPrice(purchase.getTotalPrice())
                .purchaseDate(purchase.getPurchaseDate())
                .build();
    }
}
