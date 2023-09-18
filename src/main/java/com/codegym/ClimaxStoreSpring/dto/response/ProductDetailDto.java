package com.codegym.ClimaxStoreSpring.dto.response;

import com.codegym.ClimaxStoreSpring.entity.product.Category;
import com.codegym.ClimaxStoreSpring.entity.product.Developer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailDto {
    private Long id;
    private String productName;
    private String productCoverUrl;
    private List<Category> categoryList;
    private Developer developer;
    private Double price;
    private String productDetailDescription;
    private Date releaseDate;
}
