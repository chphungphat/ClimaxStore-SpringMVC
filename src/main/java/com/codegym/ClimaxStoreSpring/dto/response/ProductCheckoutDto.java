package com.codegym.ClimaxStoreSpring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCheckoutDto {
    private Long id;
    private String productName;
    private String productCoverUrl;
    private Double price;
    private Integer quantity;
}
