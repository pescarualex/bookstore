package com.bookstore.bookstore.integrationTest.steps;

import com.bookstore.bookstore.service.ProductReviewService;
import com.bookstore.bookstore.transfer.product.ProductResponse;
import com.bookstore.bookstore.transfer.productreview.CreateProductReviewRequest;
import com.bookstore.bookstore.transfer.productreview.ProductReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ProductReviewTestSteps {

    @Autowired
    private ProductTestSteps productTestSteps;
    @Autowired
    private ProductReviewService productReviewService;

    public ProductReviewResponse creatingProductReview() {
        ProductResponse product = productTestSteps.createProduct();

        CreateProductReviewRequest request = new CreateProductReviewRequest();
        request.setProductId(product.getId());
        request.setContent("QWERTY");

        ProductReviewResponse review = productReviewService.createReview(request);

        assertThat(review, notNullValue());
        assertThat(review.getId(), greaterThan(1L));
        assertThat(review.getContent(), is(request.getContent()));
        assertThat(review.getContent(), containsString(request.getContent()));

        return review;
    }

}
