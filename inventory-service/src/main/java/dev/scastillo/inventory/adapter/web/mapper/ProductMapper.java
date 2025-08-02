package dev.scastillo.inventory.adapter.web.mapper;

import dev.scastillo.inventory.adapter.web.dto.ProductResponseDto;
import dev.scastillo.inventory.domain.service.dto.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toProductResponseDto(ProductResponse productResponse);
}

