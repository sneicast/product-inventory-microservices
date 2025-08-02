package dev.scastillo.product.adapter.web.controller;

import dev.scastillo.product.adapter.web.dto.ProductCreateRequestDto;
import dev.scastillo.product.adapter.web.dto.ProductDto;
import dev.scastillo.product.adapter.web.mapper.ProductMapper;
import dev.scastillo.product.domain.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductCreateRequestDto request){
        return productMapper.toDto(
                productService.createProduct(
                        productMapper.toDomain(request)
                )
        );
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return productMapper.toDto(productService.getProductById(id));
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(productMapper::toDto)
                .toList();
    }


}
