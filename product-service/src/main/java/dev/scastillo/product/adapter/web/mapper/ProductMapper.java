package dev.scastillo.product.adapter.web.mapper;

import dev.scastillo.product.adapter.web.dto.ProductCreateRequestDto;
import dev.scastillo.product.adapter.web.dto.ProductDto;
import dev.scastillo.product.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDomain(ProductCreateRequestDto dto);
    ProductDto toDto(Product product);
}
