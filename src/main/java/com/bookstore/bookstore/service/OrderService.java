package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.Address;
import com.bookstore.bookstore.domain.Order;
import com.bookstore.bookstore.domain.Product;
import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.persistance.OrderRepository;
import com.bookstore.bookstore.transfer.order.CreateOrderRequest;
import com.bookstore.bookstore.transfer.order.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
    }


    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        LOGGER.info("Creating order: {}", request);

        User user = userService.getUser(request.getUserId());

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

        Set<Long> productId = request.getProductId();

        for (Long prId : productId) {
            Product product = productService.getProduct(prId);
            order.addProduct(product);
        }

        Order save = orderRepository.save(order);

        return mapOrderResponse(save);
    }


    private OrderResponse mapOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setDateCreated(order.getDateCreated());
        orderResponse.setAddress(order.getAddress().getAddress());
        orderResponse.setCity(order.getAddress().getCity());
        orderResponse.setCountry(order.getAddress().getCountry());
        orderResponse.setEmail(order.getAddress().getEmail());
        orderResponse.setFirstName(order.getAddress().getFirstName());
        orderResponse.setLastName(order.getAddress().getLastName());
        orderResponse.setPhoneNumber(order.getAddress().getPhoneNumber());
        orderResponse.setProducts(order.getProducts());
        orderResponse.setUser(order.getUser());

        return orderResponse;
    }


}
