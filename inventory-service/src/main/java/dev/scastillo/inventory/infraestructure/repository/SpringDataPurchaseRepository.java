package dev.scastillo.inventory.infraestructure.repository;

import dev.scastillo.inventory.domain.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPurchaseRepository extends JpaRepository<Purchase, Long> {
}
