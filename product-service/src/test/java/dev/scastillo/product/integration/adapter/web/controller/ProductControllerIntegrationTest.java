package dev.scastillo.product.integration.adapter.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.scastillo.product.adapter.web.dto.ProductCreateRequestDto;
import dev.scastillo.product.adapter.web.dto.ProductDto;
import dev.scastillo.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void createProduct_ShouldReturnProductDto_AndPersistInDatabase() throws Exception {
        // Arrange
        ProductCreateRequestDto requestDto =  ProductCreateRequestDto.builder()
                .name("Producto Integración")
                .price(new BigDecimal("123.45"))
                .description("Descripción integración")
                .build();

        // Act
        String response = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDto productDto = objectMapper.readValue(response, ProductDto.class);

        // Assert: validación de propiedades del DTO
        assertEquals("Producto Integración", productDto.getName());
        assertEquals(new BigDecimal("123.45"), productDto.getPrice());
        assertEquals("Descripción integración", productDto.getDescription());

        // Assert: validación en base de datos
        var productoEnDb = productRepository.findById(productDto.getId());
        assertTrue(productoEnDb.isPresent());
        assertEquals("Producto Integración", productoEnDb.get().getName());
        assertEquals(new BigDecimal("123.45"), productoEnDb.get().getPrice());
        assertEquals("Descripción integración", productoEnDb.get().getDescription());
    }

    private ProductDto getProductDtoById(int id) throws Exception {
        var mvcResult = mockMvc.perform(get("/api/v1/products/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDto.class);
    }

    @Test
    void getProductById_ShouldReturnProductDto_WhenProductExists() throws Exception {
        // Crea el producto en la BD
        ProductCreateRequestDto requestDto = ProductCreateRequestDto.builder()
                .name("Producto Test")
                .price(new BigDecimal("10.00"))
                .description("Desc Test")
                .build();

        String response = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDto created = objectMapper.readValue(response, ProductDto.class);

        ProductDto dto = getProductDtoById(created.getId());

        assertNotNull(dto);
        assertEquals(created.getId(), dto.getId());
        assertEquals("Producto Test", dto.getName());
    }

    @Test
    void getProductById_ShouldReturnNotFound_WhenProductDoesNotExist() throws Exception {
        int productId = 999;

        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllProducts_ShouldReturnProductDtoList_WhenProductsExist() throws Exception {
        createProduct("Prod1", new BigDecimal("10.00"));
        createProduct("Prod2", new BigDecimal("20.00"));

        var mvcResult = mockMvc.perform(get("/api/v1/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<ProductDto> products = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ProductDto>>() {}
        );

        assertEquals(2, products.size());
        assertTrue(products.stream().anyMatch(p -> "Prod1".equals(p.getName())));
        assertTrue(products.stream().anyMatch(p -> "Prod2".equals(p.getName())));
    }

    @Test
    void getAllProducts_ShouldReturnEmptyList_WhenNoProductsExist() throws Exception {
        var mvcResult = mockMvc.perform(get("/api/v1/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<ProductDto> products = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ProductDto>>() {}
        );

        assertTrue(products.isEmpty());
    }
    void createProduct(String name, BigDecimal price) throws Exception {
        ProductCreateRequestDto dto = ProductCreateRequestDto.builder()
                .name(name)
                .price(price)
                .description("desc")
                .build();
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
