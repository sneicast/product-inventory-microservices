package dev.scastillo.inventory.domain.service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer stock;
}
