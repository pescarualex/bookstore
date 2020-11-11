package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.ProductReview;
import com.bookstore.bookstore.exception.ResourceNotFoundException;
import com.bookstore.bookstore.persistance.ProductReviewRepository;
import com.bookstore.bookstore.transfer.productreview.CreateProductReviewRequest;
import com.bookstore.bookstore.transfer.productreview.ProductReviewResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductReviewService.class);

    private final ProductReviewRepository productReviewRepository;
    private final ProductService productService;

    @Autowired
    public ProductReviewService(ProductReviewRepository productReviewRepository, ProductService productService) {
        this.productReviewRepository = productReviewRepository;
        this.productService = productService;
    }

    @Transactional
    public ProductReviewResponse createReview(CreateProductReviewRequest request) {
        LOGGER.info("Creating product review: {}", request);

        ProductReview review = new ProductReview();
        review.setContent(request.getContent());
        review.setProduct(productService.getProduct(request.getProductId()));

        ProductReview savedReview = productReviewRepository.save(review);

        return mapProductReviewResponse(savedReview);
    }


    public ProductReviewResponse getProductReview(long id) {
        LOGGER.info("Retrieving review " + id);

        ProductReview productReview = productReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review " + id + " is not found"));

        return mapProductReviewResponse(productReview);
    }

    public void deleteProductReview(long id) {
        LOGGER.info("Deleting product: " + id);

        productReviewRepository.deleteById(id);
    }


    public ProductReviewResponse mapProductReviewResponse(ProductReview review) {
        ProductReviewResponse response = new ProductReviewResponse();
        response.setId(review.getId());
        response.setContent(review.getContent());
        return response;
    }


}
