package dev.scastillo.inventory.domain.repository;

import dev.scastillo.inventory.domain.model.Purchase;

import java.util.Optional;

public interface PurchaseRepository {
    Optional<Purchase> findById(Long id);
    Purchase save(Purchase purchase);
    void deleteAll();
}
