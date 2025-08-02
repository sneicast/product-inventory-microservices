package dev.scastillo.inventory.infraestructure.repository;

import dev.scastillo.inventory.domain.model.Purchase;
import dev.scastillo.inventory.domain.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@AllArgsConstructor
public class JpaPurchaseRepository implements PurchaseRepository {
    private final SpringDataPurchaseRepository repository;

    @Override
    public Optional<Purchase> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Purchase save(Purchase purchase) {
        return repository.save(purchase);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
