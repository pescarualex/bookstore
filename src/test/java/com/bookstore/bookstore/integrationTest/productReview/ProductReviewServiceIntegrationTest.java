package com.bookstore.bookstore.integrationTest.productReview;

import com.bookstore.bookstore.exception.ResourceNotFoundException;
import com.bookstore.bookstore.integrationTest.steps.ProductReviewTestSteps;
import com.bookstore.bookstore.service.ProductReviewService;
import com.bookstore.bookstore.transfer.productreview.ProductReviewResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class ProductReviewServiceIntegrationTest {

    @Autowired
    private ProductReviewService productReviewService;
    @Autowired
    private ProductReviewTestSteps productReviewTestSteps;

    @Test
    public void creatingProductReview_whenValidRequest_thanReturnProductReview() {
        productReviewTestSteps.creatingProductReview();
    }

    @Test
    public void getProductReview_whenExistingReview_thanGetReview() {
        ProductReviewResponse response = productReviewTestSteps.creatingProductReview();

        ProductReviewResponse productReviewRequest = productReviewService.getProductReview(response.getId());

        assertThat(productReviewRequest, notNullValue());
        assertThat(productReviewRequest.getId(), is(response.getId()));
        assertThat(productReviewRequest.getContent(), is(response.getContent()));
    }

    @Test
    public void DeleteProductReview_whenExistingReview_thanReturnNoContent() {
        ProductReviewResponse response = productReviewTestSteps.creatingProductReview();

        productReviewService.deleteProductReview(response.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productReviewService.getProductReview(response.getId()));
    }

}
