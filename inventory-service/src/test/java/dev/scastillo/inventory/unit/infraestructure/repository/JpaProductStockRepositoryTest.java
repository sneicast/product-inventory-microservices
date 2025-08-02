package dev.scastillo.inventory.unit.infraestructure.repository;

import dev.scastillo.inventory.domain.model.ProductStock;
import dev.scastillo.inventory.infraestructure.repository.JpaProductStockRepository;
import dev.scastillo.inventory.infraestructure.repository.SpringDataProductStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class JpaProductStockRepositoryTest {
    private SpringDataProductStockRepository springDataProductStockRepository;
    private JpaProductStockRepository jpaProductStockRepository;

    @BeforeEach
    void setUp() {
        this.springDataProductStockRepository = mock(SpringDataProductStockRepository.class);
        this.jpaProductStockRepository = new JpaProductStockRepository(springDataProductStockRepository);
    }

    @Test
    void save_ShouldReturnSavedProductStock() {
        ProductStock productStock = ProductStock.builder()
                .productId(1)
                .quantity(10)
                .build();

        when(springDataProductStockRepository.save(productStock)).thenReturn(productStock);

        ProductStock result = jpaProductStockRepository.save(productStock);

        assertEquals(productStock, result);
        verify(springDataProductStockRepository, times(1)).save(productStock);
    }

    @Test
    void findByProductId_ShouldReturnProductStock_WhenExists() {
        ProductStock productStock = ProductStock.builder()
                .productId(1)
                .quantity(10)
                .build();

        when(springDataProductStockRepository.findByProductId(1))
                .thenReturn(Optional.of(productStock));

        Optional<ProductStock> result = jpaProductStockRepository.findByProductId(1);

        assertEquals(Optional.of(productStock), result);
        verify(springDataProductStockRepository, times(1)).findByProductId(1);
    }

    @Test
    void findByProductId_ShouldReturnEmpty_WhenNotExists() {
        when(springDataProductStockRepository.findByProductId(2))
                .thenReturn(Optional.empty());

        Optional<ProductStock> result = jpaProductStockRepository.findByProductId(2);

        assertEquals(Optional.empty(), result);
        verify(springDataProductStockRepository, times(1)).findByProductId(2);
    }

}
