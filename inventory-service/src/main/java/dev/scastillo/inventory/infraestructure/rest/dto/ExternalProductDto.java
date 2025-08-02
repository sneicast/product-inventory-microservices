package dev.scastillo.inventory.infraestructure.rest.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalProductDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
}
