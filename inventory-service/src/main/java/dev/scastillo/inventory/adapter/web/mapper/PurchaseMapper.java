package dev.scastillo.inventory.adapter.web.mapper;

import dev.scastillo.inventory.adapter.web.dto.PurchaseResponseDto;
import dev.scastillo.inventory.domain.service.dto.PurchaseResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    PurchaseResponseDto toDto (PurchaseResponse purchaseResponse);
}
