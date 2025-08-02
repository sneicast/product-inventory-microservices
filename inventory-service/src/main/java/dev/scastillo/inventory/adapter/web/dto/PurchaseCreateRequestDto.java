package dev.scastillo.inventory.adapter.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseCreateRequestDto {
    private Integer productId;
    private Integer quantity;

}
