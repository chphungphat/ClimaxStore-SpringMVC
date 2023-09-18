package com.codegym.ClimaxStoreSpring.service.Impl;

import com.codegym.ClimaxStoreSpring.entity.product.Product;
import com.codegym.ClimaxStoreSpring.entity.user.User;
import com.codegym.ClimaxStoreSpring.repository.ProductRepository;
import com.codegym.ClimaxStoreSpring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) throws EntityNotFoundException {
        Optional<Product> findProduct = productRepository.findById(id);
        if (findProduct.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        } else {
            product.setId(id);
            return productRepository.save(product);
        }
    }

    @Override
    public void remove(Long id) throws EntityNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productRepository.delete(product);
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
    }

    @Override
    public Page<Product> getProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findAll(pageable);
    }
}
