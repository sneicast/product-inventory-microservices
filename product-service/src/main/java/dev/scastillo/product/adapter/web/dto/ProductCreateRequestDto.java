package dev.scastillo.product.adapter.web.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateRequestDto {
    private String name;
    private BigDecimal price;
    private String description;
}
