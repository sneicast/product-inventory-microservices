package dev.scastillo.inventory.unit.infraestructure.rest;

import dev.scastillo.inventory.infraestructure.rest.ProductRestClient;
import dev.scastillo.inventory.infraestructure.rest.dto.ExternalProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductRestClientTest {
    private RestTemplate restTemplate;
    private ProductRestClient productRestClient;

    @BeforeEach
    void setUp() throws Exception {
        restTemplate = mock(RestTemplate.class);
        productRestClient = new ProductRestClient(restTemplate);

        Field field = ProductRestClient.class.getDeclaredField("productApiBaseUrl");
        field.setAccessible(true);
        field.set(productRestClient, "http://fake-url");
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenExists() throws Exception {
        Integer productId = 1;
        ExternalProductDto product = ExternalProductDto.builder().id(productId).name("Test").build();
        ResponseEntity<ExternalProductDto> responseEntity = ResponseEntity.ok(product);

        // Configura el apiKey en el cliente
        Field apiKeyField = ProductRestClient.class.getDeclaredField("apiKey");
        apiKeyField.setAccessible(true);
        apiKeyField.set(productRestClient, "dummy-key");

        String expectedUrl = "http://fake-url/api/v1/products/1";

        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(org.springframework.http.HttpMethod.GET),
                any(org.springframework.http.HttpEntity.class),
                eq(ExternalProductDto.class)
        )).thenReturn(responseEntity);

        Optional<ExternalProductDto> result = productRestClient.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getId());
        assertEquals("Test", result.get().getName());
        verify(restTemplate).exchange(
                eq(expectedUrl),
                eq(org.springframework.http.HttpMethod.GET),
                any(org.springframework.http.HttpEntity.class),
                eq(ExternalProductDto.class)
        );
    }

    @Test
    void getProductById_ShouldReturnEmpty_WhenNotFound() throws Exception {
        Integer productId = 99;

        // Configura el apiKey en el cliente
        Field apiKeyField = ProductRestClient.class.getDeclaredField("apiKey");
        apiKeyField.setAccessible(true);
        apiKeyField.set(productRestClient, "dummy-key");

        String expectedUrl = "http://fake-url/api/v1/products/99";

        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(org.springframework.http.HttpMethod.GET),
                any(org.springframework.http.HttpEntity.class),
                eq(ExternalProductDto.class)
        )).thenThrow(HttpClientErrorException.NotFound.class);

        Optional<ExternalProductDto> result = productRestClient.getProductById(productId);

        assertFalse(result.isPresent());
        verify(restTemplate).exchange(
                eq(expectedUrl),
                eq(org.springframework.http.HttpMethod.GET),
                any(org.springframework.http.HttpEntity.class),
                eq(ExternalProductDto.class)
        );
    }
}
