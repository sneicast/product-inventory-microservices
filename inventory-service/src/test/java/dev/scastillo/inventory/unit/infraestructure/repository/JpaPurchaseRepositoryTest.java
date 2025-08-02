package dev.scastillo.inventory.unit.infraestructure.repository;

import dev.scastillo.inventory.domain.model.Purchase;
import dev.scastillo.inventory.infraestructure.repository.JpaPurchaseRepository;
import dev.scastillo.inventory.infraestructure.repository.SpringDataPurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JpaPurchaseRepositoryTest {
    private SpringDataPurchaseRepository springDataPurchaseRepository;
    private JpaPurchaseRepository jpaPurchaseRepository;

    @BeforeEach
    void setUp() {
        this.springDataPurchaseRepository = mock(SpringDataPurchaseRepository.class);
        this.jpaPurchaseRepository = new JpaPurchaseRepository(springDataPurchaseRepository);
    }

    @Test
    void findById_ShouldReturnPurchase_WhenExists() {
        Purchase purchase = Purchase.builder()
                .id(1L)
                .productId(10)
                .quantity(2)
                .build();

        when(springDataPurchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));

        Optional<Purchase> result = jpaPurchaseRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(purchase, result.get());
        verify(springDataPurchaseRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        when(springDataPurchaseRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Purchase> result = jpaPurchaseRepository.findById(2L);

        assertFalse(result.isPresent());
        verify(springDataPurchaseRepository, times(1)).findById(2L);
    }

    @Test
    void save_ShouldReturnSavedPurchase() {
        Purchase purchase = Purchase.builder()
                .id(1L)
                .productId(10)
                .quantity(2)
                .build();

        when(springDataPurchaseRepository.save(purchase)).thenReturn(purchase);

        Purchase result = jpaPurchaseRepository.save(purchase);

        assertEquals(purchase, result);
        verify(springDataPurchaseRepository, times(1)).save(purchase);
    }

}
