package dev.scastillo.product.unit.infraestructure.repository;

import dev.scastillo.product.domain.model.Product;
import dev.scastillo.product.infraestructure.repository.JpaProductRepository;
import dev.scastillo.product.infraestructure.repository.SpringDataProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JpaProductRepositoryTest {

    private SpringDataProductRepository springDataProductRepository;
    private JpaProductRepository jpaProductRepository;

    @BeforeEach
    void setUp() {
        this.springDataProductRepository = mock(SpringDataProductRepository.class);
        this.jpaProductRepository = new JpaProductRepository(springDataProductRepository);
    }

    @Test
    void save_ShouldCreateProductSuccessfully() {
        // Arrange
        Product product = Product.builder()
                .id(1)
                .name("Producto Test")
                .price(new BigDecimal("99.99"))
                .description("Descripción del producto de prueba")
                .build();
        when(springDataProductRepository.save(product)).thenReturn(product);

        // Act
        Product saved = jpaProductRepository.save(product);

        // Assert
        assertEquals(product, saved);
        verify(springDataProductRepository).save(product);
    }

    @Test
    void findById_ShouldReturnProduct_WhenExists() {
        // Arrange
        Product product = Product.builder()
                .id(1)
                .name("Producto Test")
                .price(new BigDecimal("99.99"))
                .description("Descripción del producto de prueba")
                .build();
        when(springDataProductRepository.findById(1)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = jpaProductRepository.findById(1);

        // Assert
        assertEquals(Optional.of(product), result);
        verify(springDataProductRepository).findById(1);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotFound() {
        // Arrange
        when(springDataProductRepository.findById(2)).thenReturn(Optional.empty());

        // Act
        Optional<Product> result = jpaProductRepository.findById(2);

        // Assert
        assertEquals(Optional.empty(), result);
        verify(springDataProductRepository).findById(2);
    }

    @Test
    void findAll_ShouldReturnProductList_WhenProductsExist() {
        // Arrange
        Product product1 = Product.builder()
                .id(1)
                .name("Producto 1")
                .price(new BigDecimal("10.00"))
                .description("Desc 1")
                .build();
        Product product2 = Product.builder()
                .id(2)
                .name("Producto 2")
                .price(new BigDecimal("20.00"))
                .description("Desc 2")
                .build();
        List<Product> products = List.of(product1, product2);
        when(springDataProductRepository.findAll()).thenReturn(products);

        // Act
        List<Product> result = jpaProductRepository.findAll();

        // Assert
        assertEquals(products, result);
        verify(springDataProductRepository).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoProductsExist() {
        // Arrange
        when(springDataProductRepository.findAll()).thenReturn(List.of());

        // Act
        List<Product> result = jpaProductRepository.findAll();

        // Assert
        assertEquals(List.of(), result);
        verify(springDataProductRepository).findAll();
    }
}
