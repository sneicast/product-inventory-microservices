package dev.scastillo.inventory.domain.service;

import dev.scastillo.inventory.infraestructure.rest.dto.ExternalProductDto;

import java.util.Optional;

public interface ProductServicePort {
    Optional<ExternalProductDto> getProductById(Integer id);
}
