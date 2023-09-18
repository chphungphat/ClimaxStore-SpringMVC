package com.codegym.ClimaxStoreSpring.service.Impl;

import com.codegym.ClimaxStoreSpring.entity.product.Category;
import com.codegym.ClimaxStoreSpring.entity.product.Product;
import com.codegym.ClimaxStoreSpring.repository.CategoryRepository;
import com.codegym.ClimaxStoreSpring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) throws EntityNotFoundException {
        Optional<Category> findProduct = categoryRepository.findById(id);
        if (findProduct.isEmpty()) {
            throw new EntityNotFoundException("Category not found");
        } else {
            category.setId(id);
            return categoryRepository.save(category);
        }
    }

    @Override
    public void remove(Long id) throws EntityNotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            categoryRepository.delete(category);
        } else {
            throw new EntityNotFoundException("Category not found with ID: " + id);
        }
    }
}
