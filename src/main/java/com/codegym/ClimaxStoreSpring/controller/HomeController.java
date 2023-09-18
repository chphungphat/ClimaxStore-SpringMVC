package com.codegym.ClimaxStoreSpring.controller;

import com.codegym.ClimaxStoreSpring.converter.ProductConverter;
import com.codegym.ClimaxStoreSpring.dto.request.UserLoginDto;
import com.codegym.ClimaxStoreSpring.dto.request.UserRegisterDto;
import com.codegym.ClimaxStoreSpring.dto.response.ProductCheckoutDto;
import com.codegym.ClimaxStoreSpring.dto.response.ProductDetailDto;
import com.codegym.ClimaxStoreSpring.dto.response.ProductResponseDto;
import com.codegym.ClimaxStoreSpring.entity.business.CartDetail;
import com.codegym.ClimaxStoreSpring.entity.product.Category;
import com.codegym.ClimaxStoreSpring.entity.product.Product;
import com.codegym.ClimaxStoreSpring.entity.user.User;
import com.codegym.ClimaxStoreSpring.entity.user.UserDetail;
import com.codegym.ClimaxStoreSpring.security.AuthUserDetails;
import com.codegym.ClimaxStoreSpring.security.UserDetailsServiceImpl;
import com.codegym.ClimaxStoreSpring.service.CartDetailService;
import com.codegym.ClimaxStoreSpring.service.CategoryService;
import com.codegym.ClimaxStoreSpring.service.ProductService;
import com.codegym.ClimaxStoreSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CartDetailService cartDetailService;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("userLoginDto", new UserLoginDto());
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("userRegisterDto", new UserRegisterDto());
        return modelAndView;
    }

//    @PostMapping("/login")
//    public ModelAndView login(@ModelAttribute("userLoginDto") UserLoginDto userLoginDto) {
//        ModelAndView modelAndView;
//        User user = userService.loginUser(userLoginDto);
//        if (user == null) {
//            modelAndView = new ModelAndView("login");
//            modelAndView.addObject("message", "Invalid input");
//        } else {
//            modelAndView = new ModelAndView("products");
//        }
//        return modelAndView;
//    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findByUserName(username);

        if (user == null) {
            modelAndView.setViewName("login");
            modelAndView.addObject("message", "Invalid input");
        } else {
            if (passwordEncoder.matches(password, user.getPassword())) {
                AuthUserDetails userDetails = new AuthUserDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

                Authentication authenticated = authenticationManager.authenticate(authentication);

                SecurityContextHolder.getContext().setAuthentication(authenticated);

                modelAndView.setViewName("redirect:/products");
            } else {
                modelAndView.setViewName("login");
                modelAndView.addObject("message", "Invalid input");
            }
        }

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("userRegisterDto") UserRegisterDto userRegisterDto, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("register");
        if (result.hasErrors()) {
            modelAndView.addObject("message", "Invalid input");
        } else {
            User user = userService.registerUser(userRegisterDto);
            if (user != null) {
                modelAndView.addObject("message", "register new user successfully");
            }
        }
        return modelAndView;
    }


    @GetMapping("/products")
    public ModelAndView getProducts(@RequestParam(value = "page", defaultValue = "0") int page) {
        int pageSize = 10;
        Page<Product> productPage = productService.getProducts(page, pageSize);
        Page<ProductResponseDto> productResponsePage = productPage.map(ProductConverter::convertToProductResponseDto);
        ModelAndView modelAndView = new ModelAndView("products");
        modelAndView.addObject("productPage", productResponsePage);


        List<Category> categoryList = categoryService.findAll();
        modelAndView.addObject("categoryList", categoryList);

        return modelAndView;
    }

    @GetMapping("/products/{id}")
    public ModelAndView getProductDetail(@PathVariable("id") Long id) {
        Optional<Product> productOption = productService.findById(id);
        ProductDetailDto productDetailDto = new ProductDetailDto();
        if (productOption.isPresent()) {
            productDetailDto = ProductConverter.convertToProductDetailDto(productOption.get());
        }
        ModelAndView modelAndView = new ModelAndView("product_detail");
        modelAndView.addObject("productDetail", productDetailDto);

        List<Category> categoryList = categoryService.findAll();
        modelAndView.addObject("categoryList", categoryList);

        return modelAndView;
    }

    @PostMapping("/products/add/{id}")
    public ModelAndView addToCart(@PathVariable("id") Long id, Authentication authentication) {
        AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
        String username = authUserDetails.getUsername();
        User user = userService.findByUserName(username);
        Long userId = user.getId();
        cartDetailService.addToCart(id, userId, 1);
        return new ModelAndView("redirect:/products/" + id);
    }

    @GetMapping("/cart")
    public ModelAndView viewCart() {
        Long userId = ((UserDetailsServiceImpl) userDetailsService).getCurrentUserId();
        Optional<User> user = userService.findById(userId);

        List<CartDetail> cartDetails = cartDetailService.findByUser(user.get());
        List<ProductCheckoutDto> productCartList = new ArrayList<>();

        for (CartDetail cartDetail : cartDetails) {
            ProductCheckoutDto productCheckoutDto = ProductConverter.convertToProductCheckoutDto(cartDetail);
            productCartList.add(productCheckoutDto);
        }

        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("productCartList", productCartList);

        List<Category> categoryList = categoryService.findAll();
        modelAndView.addObject("categoryList", categoryList);

        return modelAndView;

    }

//    @GetMapping("/products")
//    public ModelAndView get() {
//        return new ModelAndView("navbar");
//    }
}

