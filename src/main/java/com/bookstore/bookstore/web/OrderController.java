package com.bookstore.bookstore.web;

import com.bookstore.bookstore.domain.Order;
import com.bookstore.bookstore.service.OrderService;
import com.bookstore.bookstore.transfer.order.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
