package dev.scastillo.inventory.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer stock;
}
