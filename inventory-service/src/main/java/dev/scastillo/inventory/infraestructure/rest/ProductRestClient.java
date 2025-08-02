package dev.scastillo.inventory.infraestructure.rest;

import dev.scastillo.inventory.infraestructure.rest.dto.ExternalProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ProductRestClient {
    private final RestTemplate restTemplate;

    @Value("${product.api.base-url}")
    private String productApiBaseUrl;

    public ProductRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<ExternalProductDto> getProductById(Integer productId) {
        try {
            String url = productApiBaseUrl + "/api/v1/products/" + productId;
            ResponseEntity<ExternalProductDto> response = restTemplate.getForEntity(url, ExternalProductDto.class
            );
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
}
