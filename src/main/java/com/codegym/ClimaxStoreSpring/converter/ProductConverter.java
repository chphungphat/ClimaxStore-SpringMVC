package com.codegym.ClimaxStoreSpring.converter;

import com.codegym.ClimaxStoreSpring.dto.response.ProductCheckoutDto;
import com.codegym.ClimaxStoreSpring.dto.response.ProductDetailDto;
import com.codegym.ClimaxStoreSpring.dto.response.ProductResponseDto;
import com.codegym.ClimaxStoreSpring.entity.business.CartDetail;
import com.codegym.ClimaxStoreSpring.entity.product.Product;

public class ProductConverter {
    public static ProductResponseDto convertToProductResponseDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setProductName(product.getProductName());
        productResponseDto.setProductCoverUrl(product.getProductCoverUrl());
        productResponseDto.setCategoryList(product.getCategoryList());
        productResponseDto.setPrice(product.getPrice());
        return productResponseDto;
    }

    public static ProductDetailDto convertToProductDetailDto(Product product) {

        return ProductDetailDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productCoverUrl(product.getProductCoverUrl())
                .categoryList(product.getCategoryList())
                .developer(product.getDeveloper())
                .price(product.getPrice())
                .productDetailDescription(product.getProductDetailDescription())
                .releaseDate(product.getReleaseDate())
                .build();
    }

    public static ProductCheckoutDto convertToProductCheckoutDto(CartDetail cartDetail) {
        ProductCheckoutDto productCheckoutDto = new ProductCheckoutDto();
        productCheckoutDto.setId(cartDetail.getProduct().getId());
        productCheckoutDto.setProductName(cartDetail.getProduct().getProductName());
        productCheckoutDto.setProductCoverUrl(cartDetail.getProduct().getProductCoverUrl());
        productCheckoutDto.setPrice(cartDetail.getProduct().getPrice());
        productCheckoutDto.setQuantity(cartDetail.getQuantity());
        return productCheckoutDto;
    }
}
