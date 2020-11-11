package com.bookstore.bookstore.integrationTest.steps;

import com.bookstore.bookstore.service.ProductService;
import com.bookstore.bookstore.transfer.product.CreateProductRequest;
import com.bookstore.bookstore.transfer.product.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ProductTestSteps {

    @Autowired
    private ProductService productService;


    public ProductResponse createProduct() {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Test Product " + System.currentTimeMillis());
        request.setAuthor("Sadoveanu");
        request.setDescription("qwerty");
        request.setPrice(28.99);
        request.setQuantity(10);

        ProductResponse product = productService.createProduct(request);

        assertThat(product, notNullValue());
        assertThat(product.getId(), notNullValue());
        assertThat(product.getId(), greaterThan(0L));
        assertThat(product.getName(), is(request.getName()));
        assertThat(product.getDescription(), is(request.getDescription()));
        assertThat(product.getPrice(), is(request.getPrice()));
        assertThat(product.getAuthor(), is(request.getAuthor()));
        assertThat(product.getQuantity(), is(request.getQuantity()));

        return product;
    }

}
