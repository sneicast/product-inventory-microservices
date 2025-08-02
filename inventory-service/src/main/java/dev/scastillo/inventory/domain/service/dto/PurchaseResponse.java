package dev.scastillo.inventory.domain.service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseResponse {
    private Long id;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private OffsetDateTime purchaseDate;
}
