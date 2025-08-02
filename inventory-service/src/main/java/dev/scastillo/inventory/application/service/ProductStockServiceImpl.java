package dev.scastillo.inventory.application.service;

import dev.scastillo.inventory.domain.model.ProductStock;
import dev.scastillo.inventory.domain.repository.ProductStockRepository;
import dev.scastillo.inventory.domain.service.ProductServicePort;
import dev.scastillo.inventory.domain.service.ProductStockService;
import dev.scastillo.inventory.domain.service.dto.ProductResponse;
import dev.scastillo.inventory.infraestructure.rest.dto.ExternalProductDto;
import dev.scastillo.inventory.shared.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductStockServiceImpl implements ProductStockService {
    private final ProductStockRepository productStockRepository;
    private final ProductServicePort productServicePort;

    @Override
    public ProductResponse getDetailProductById(Integer productId) {
        ExternalProductDto externalProduct = getProductById(productId);
        Integer stockQuantity = productStockRepository.findByProductId(productId)
                .map(ProductStock::getQuantity)
                .orElse(0);
        return mapToProductResponse(stockQuantity, externalProduct);
    }

    @Override
    public ProductResponse updateProductStock(Integer productId, Integer stock) {
        ExternalProductDto externalProduct = getProductById(productId);

        ProductStock productStock = productStockRepository.findByProductId(productId)
                .orElseGet(() -> {
                    ProductStock newProductStock = new ProductStock();
                    newProductStock.setProductId(externalProduct.getId());
                    return newProductStock;
                });

        productStock.setQuantity(stock);
        productStockRepository.save(productStock);

        return mapToProductResponse(stock, externalProduct);
    }

    private ProductResponse mapToProductResponse(Integer stockQuantity, ExternalProductDto productDto) {
        return ProductResponse.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stock(stockQuantity)
                .build();
    }

    private ExternalProductDto getProductById(Integer productId) {
        return productServicePort.getProductById(productId)
                .orElseThrow(() -> new NotFoundException( "Producto no encontrado id: " + productId));
    }
}
