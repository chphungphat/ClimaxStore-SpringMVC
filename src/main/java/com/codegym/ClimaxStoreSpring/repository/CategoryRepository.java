package com.codegym.ClimaxStoreSpring.repository;

import com.codegym.ClimaxStoreSpring.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
