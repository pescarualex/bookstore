package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.Cart;
import com.bookstore.bookstore.domain.Product;
import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.exception.ResourceNotFoundException;
import com.bookstore.bookstore.persistance.CartRepository;
import com.bookstore.bookstore.transfer.cart.AddProductToCartRequest;
import com.bookstore.bookstore.transfer.cart.CartResponse;
import com.bookstore.bookstore.transfer.cart.ProductInCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;


    @Autowired
    public CartService(CartRepository cartRepository, UserService userService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }


    @Transactional
    public void addProductToCart(AddProductToCartRequest request) {
        LOGGER.info("Adding product to cart: {}", request);

        Cart cart = cartRepository.findById(request.getUserId()).orElse(new Cart());

        if (cart.getUser() == null) {
            User user = userService.getUser(request.getUserId());
            cart.setUser(user);
        }

        Product product = productService.getProduct(request.getProductId());

        cart.addProduct(product);
        cartRepository.save(cart);
    }


    @Transactional
    public CartResponse getCart(long userId) {
        LOGGER.info("Retrieving cart for user {}", userId);

        Cart cart = cartRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart " + userId + " does not exist"));

        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());

        Set<ProductInCart> products = new HashSet<>();

        for (Product product : cart.getProducts()) {

            ProductInCart productInCart = new ProductInCart();
            productInCart.setId(product.getId());
            productInCart.setName(product.getName());
            productInCart.setPrice(product.getPrice());
            productInCart.setImageUrl(product.getImageUrl());

            products.add(productInCart);
        }

        cartResponse.setProducts(products);

        return cartResponse;
    }

    public double getTotalPrice(long cartId) {
        LOGGER.info("Retrieving total price for cart: {}", cartId);

        Cart cart = cartRepository.findById(cartId).orElse(new Cart());
        Set<Product> products = cart.getProducts();

        for (Product product : products) {
            cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
        }

        return cart.getTotalPrice();
    }


    @Transactional
    public void deleteProductFromCart(long userId, long productId) {
        LOGGER.info("Deleting product: {} from cart for user: {} ", productId, userId);

        Cart cart = cartRepository.findById(userId).orElse(new Cart());

        if (cart.getUser() == null) {
            User user = userService.getUser(userId);
            cart.setUser(user);
        }

        Product product = productService.getProduct(productId);

        cart.removeProduct(product);
        cartRepository.save(cart);
    }

    public void deleteCart(long id) {
        LOGGER.info("Deleting cart: {}", id);

        cartRepository.deleteById(id);
    }

}
