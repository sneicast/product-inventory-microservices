package dev.scastillo.inventory.infraestructure.rest;

import dev.scastillo.inventory.domain.service.ProductServicePort;
import dev.scastillo.inventory.infraestructure.rest.dto.ExternalProductDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductServiceAdapter implements ProductServicePort {
    private final ProductRestClient client;
    @Override
    public Optional<ExternalProductDto> getProductById(Integer id) {
        return client.getProductById(id);
    }
}
