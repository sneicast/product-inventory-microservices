package dev.scastillo.inventory.unit.infraestructure.rest;

import dev.scastillo.inventory.infraestructure.rest.ProductRestClient;
import dev.scastillo.inventory.infraestructure.rest.ProductServiceAdapter;
import dev.scastillo.inventory.infraestructure.rest.dto.ExternalProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceAdapterTest {
    private ProductRestClient client;
    private ProductServiceAdapter adapter;

    @BeforeEach
    void setUp() {
        client = mock(ProductRestClient.class);
        adapter = new ProductServiceAdapter(client);
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenExists() {
        Integer productId = 1;
        ExternalProductDto product = ExternalProductDto.builder().id(productId).name("Test").build();
        when(client.getProductById(productId)).thenReturn(Optional.of(product));

        Optional<ExternalProductDto> result = adapter.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getId());
        assertEquals("Test", result.get().getName());
        verify(client).getProductById(productId);
    }

    @Test
    void getProductById_ShouldReturnEmpty_WhenNotExists() {
        Integer productId = 99;
        when(client.getProductById(productId)).thenReturn(Optional.empty());

        Optional<ExternalProductDto> result = adapter.getProductById(productId);

        assertFalse(result.isPresent());
        verify(client).getProductById(productId);
    }
}
