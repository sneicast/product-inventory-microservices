package dev.scastillo.inventory.adapter.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStockRequestDto {
    private Integer quantity;
}
