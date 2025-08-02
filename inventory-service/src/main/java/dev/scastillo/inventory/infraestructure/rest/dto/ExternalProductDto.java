package dev.scastillo.inventory.infraestructure.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExternalProductDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
}
