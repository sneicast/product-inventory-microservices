package dev.scastillo.inventory.infraestructure.repository;

import dev.scastillo.inventory.domain.model.ProductStock;
import dev.scastillo.inventory.domain.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class JpaProductStockRepository implements ProductStockRepository {
    private final SpringDataProductStockRepository repository;
    @Override
    public ProductStock save(ProductStock productStock) {
        return repository.save(productStock);
    }

    @Override
    public Optional<ProductStock> findByProductId(Integer productId) {
        return repository.findByProductId(productId);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
