package com.codegym.ClimaxStoreSpring.repository;

import com.codegym.ClimaxStoreSpring.entity.business.CartDetail;
import com.codegym.ClimaxStoreSpring.entity.product.Product;
import com.codegym.ClimaxStoreSpring.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    CartDetail findCartDetailByUserAndProduct(User user, Product product);

    List<CartDetail> findCartDetailByUser(User user);
}
