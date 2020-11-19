package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.Address;
import com.bookstore.bookstore.domain.Order;
import com.bookstore.bookstore.domain.Product;
import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.persistance.OrderRepository;
import com.bookstore.bookstore.transfer.cart.ProductInCart;
import com.bookstore.bookstore.transfer.order.CreateOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }


    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        LOGGER.info("Creating order: {}", request);

        User user = userService.getUser(request.getUserId());

        Set<ProductInCart> products = cartService.getCart(user.getId()).getProducts();


        Address address = new Address();
        address.setFirstName(request.getFirstName());
        address.setLastName(request.getLastName());
        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setEmail(request.getEmail());
        address.setPhoneNumber(request.getPhoneNumber());

        Order order = new Order();
        order.setAddress(address);
        order.setDateCreated(LocalDate.now());
        order.setUser(user);
        order.setProducts(products);


        return orderRepository.save(order);
    }






}
