package dev.scastillo.inventory.adapter.web.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer stock;
}
