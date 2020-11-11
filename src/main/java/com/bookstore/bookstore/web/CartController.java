package com.bookstore.bookstore.web;

import com.bookstore.bookstore.service.CartService;
import com.bookstore.bookstore.transfer.cart.AddProductToCartRequest;
import com.bookstore.bookstore.transfer.cart.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping
    public ResponseEntity<Void> AddProductToCart(@RequestBody @Valid AddProductToCartRequest request) {
        cartService.addProductToCart(request);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable long id) {
        CartResponse cart = cartService.getCart(id);

        return ResponseEntity.ok(cart);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@RequestParam long userId) {
        cartService.deleteCart(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable long userId, @PathVariable long productId) {
        cartService.deleteProductFromCart(userId, productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
