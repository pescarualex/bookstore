package com.bookstore.bookstore.integrationTest.cart;

import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.exception.ResourceNotFoundException;
import com.bookstore.bookstore.integrationTest.steps.ProductTestSteps;
import com.bookstore.bookstore.integrationTest.steps.UserTestSteps;
import com.bookstore.bookstore.service.CartService;
import com.bookstore.bookstore.transfer.cart.AddProductToCartRequest;
import com.bookstore.bookstore.transfer.cart.CartResponse;
import com.bookstore.bookstore.transfer.cart.ProductInCart;
import com.bookstore.bookstore.transfer.product.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
public class CartServiceIntegrationTest {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserTestSteps userTestSteps;
    @Autowired
    private ProductTestSteps productTestSteps;


    @Test
    public void addProductToCart_whenValidRequest_thanProductsAreAddedToCart() {
        User user = userTestSteps.createUser();

        ProductResponse product = productTestSteps.createProduct();

        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setUserId(user.getId());
        request.setProductId(product.getId());

        cartService.addProductToCart(request);

        CartResponse cart = cartService.getCart(request.getUserId());

        assertThat(cart, notNullValue());
        assertThat(cart.getId(), is(request.getUserId()));

        assertThat(cart.getProducts(), notNullValue());
        assertThat(cart.getProducts(), hasSize(1));

        ProductInCart productInCart = cart.getProducts().iterator().next();

        assertThat(productInCart, notNullValue());
        assertThat(productInCart.getId(), is(product.getId()));
        assertThat(productInCart.getName(), is(product.getName()));
        assertThat(productInCart.getPrice(), is(product.getPrice()));
        assertThat(productInCart.getImageUrl(), is(product.getImageUrl()));

    }


    @Test
    public void getCart_whenMissingCart_thanReturnResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> cartService.getCart(1000));
    }


    @Test
    public void getCart_whenExistingCart_thanReturnCart() {
        User user = userTestSteps.createUser();

        ProductResponse product = productTestSteps.createProduct();

        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setUserId(user.getId());
        request.setProductId(product.getId());

        cartService.addProductToCart(request);

        CartResponse cart = cartService.getCart(request.getUserId());

        assertThat(cart, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(cart.getId(), is(user.getId()));
        assertThat(product, notNullValue());
        assertThat(cart.getProducts(), notNullValue());
        assertThat(cart.getProducts(), hasSize(1));
    }


    @Test
    public void getCart_whenDoesNotExistingCart_thanReturnResourceNotFoundException() {
        User user = userTestSteps.createUser();

        ProductResponse product = productTestSteps.createProduct();

        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setUserId(user.getId());
        request.setProductId(product.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> cartService.getCart(user.getId()));
    }

    @Test
    public void deleteCart_whenExistingCart_thanReturnDeletedCart() {
        User user = userTestSteps.createUser();

        ProductResponse product = productTestSteps.createProduct();

        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setUserId(user.getId());
        request.setProductId(product.getId());

        cartService.addProductToCart(request);

        CartResponse cart = cartService.getCart(request.getUserId());

        assertThat(cart, notNullValue());

        cartService.deleteCart(cart.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> cartService.getCart(request.getUserId()));
    }




}