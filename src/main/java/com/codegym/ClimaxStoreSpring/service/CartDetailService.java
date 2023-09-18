package com.codegym.ClimaxStoreSpring.service;

import com.codegym.ClimaxStoreSpring.entity.business.CartDetail;
import com.codegym.ClimaxStoreSpring.entity.user.User;

import java.util.List;

public interface CartDetailService extends GenericService<CartDetail> {
    void addToCart(Long productId, Long userId, Integer quantity);

    List<CartDetail> findByUser(User user);
}
