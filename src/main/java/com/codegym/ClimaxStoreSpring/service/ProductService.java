package com.codegym.ClimaxStoreSpring.service;

import com.codegym.ClimaxStoreSpring.entity.product.Product;
import org.springframework.data.domain.Page;

public interface ProductService extends GenericService<Product> {
    Page<Product> getProducts(int page, int pageSize);
}
