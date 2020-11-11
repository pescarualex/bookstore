package com.bookstore.bookstore.web;

import com.bookstore.bookstore.service.ProductReviewService;
import com.bookstore.bookstore.transfer.productreview.CreateProductReviewRequest;
import com.bookstore.bookstore.transfer.productreview.ProductReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping("/review")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @Autowired
    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @PostMapping
    public ResponseEntity<ProductReviewResponse> createReview(@RequestBody CreateProductReviewRequest request) {
        ProductReviewResponse review = productReviewService.createReview(request);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReviewResponse> getProductReview(@PathVariable long id) {
        ProductReviewResponse productReview = productReviewService.getProductReview(id);

        return new ResponseEntity<>(productReview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productReviewService.deleteProductReview(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
