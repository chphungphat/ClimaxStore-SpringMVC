package com.codegym.ClimaxStoreSpring.service.Impl;

import com.codegym.ClimaxStoreSpring.entity.business.CartDetail;
import com.codegym.ClimaxStoreSpring.entity.product.Product;
import com.codegym.ClimaxStoreSpring.entity.user.User;
import com.codegym.ClimaxStoreSpring.repository.CartDetailRepository;
import com.codegym.ClimaxStoreSpring.service.CartDetailService;
import com.codegym.ClimaxStoreSpring.service.ProductService;
import com.codegym.ClimaxStoreSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public List<CartDetail> findAll() {
        return cartDetailRepository.findAll();
    }

    @Override
    public Optional<CartDetail> findById(Long id) {
        return cartDetailRepository.findById(id);
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public CartDetail update(Long id, CartDetail cartDetail) throws EntityNotFoundException {
        Optional<CartDetail> findCartDetail = cartDetailRepository.findById(id);
        if (findCartDetail.isEmpty()) {
            throw new EntityNotFoundException("Cart Detail not found");
        } else {
            cartDetail.setId(id);
            return cartDetailRepository.save(cartDetail);
        }
    }

    @Override
    public void remove(Long id) throws EntityNotFoundException {
        Optional<CartDetail> cartDetail = cartDetailRepository.findById(id);
        if (cartDetail.isPresent()) {
            CartDetail cartDetail1 = cartDetail.get();
            cartDetailRepository.delete(cartDetail1);
        } else {
            throw new EntityNotFoundException("Cart Detail found with ID: " + id);
        }
    }

    @Override
    public void addToCart(Long productId, Long userId, Integer quantity) {
        Optional<Product> product = productService.findById(productId);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Invalid product");
        }
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid user");
        }

        CartDetail cartDetail = cartDetailRepository.findCartDetailByUserAndProduct(user.get(), product.get());

        if (cartDetail == null) {
            cartDetail = new CartDetail();
            cartDetail.setProduct(product.get());
            cartDetail.setUser(user.get());
            cartDetail.setQuantity(quantity);
            cartDetail.setSubTotalPrice(product.get().getPrice() * quantity);
        } else {
            int newQuantity = cartDetail.getQuantity() + quantity;
            double newSubTotalPrice = product.get().getPrice() * newQuantity;

            cartDetail.setQuantity(newQuantity);
            cartDetail.setSubTotalPrice(newSubTotalPrice);
        }

        cartDetailRepository.save(cartDetail);
    }

    @Override
    public List<CartDetail> findByUser(User user) {
        return cartDetailRepository.findCartDetailByUser(user);
    }
}
