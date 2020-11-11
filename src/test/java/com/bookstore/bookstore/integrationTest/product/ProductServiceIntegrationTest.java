package com.bookstore.bookstore.integrationTest.product;

import com.bookstore.bookstore.domain.Product;
import com.bookstore.bookstore.exception.ResourceNotFoundException;
import com.bookstore.bookstore.integrationTest.steps.ProductTestSteps;
import com.bookstore.bookstore.service.ProductService;
import com.bookstore.bookstore.transfer.product.ProductResponse;
import com.bookstore.bookstore.transfer.product.UpdateProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    public void createProduct_whenValidRequest_thenReturnCreatedProduct() {
        productTestSteps.createProduct();
    }

    @Test
    public void createProduct_whenMissingProduct_thenThrownNullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
                () -> productService.createProduct(null));
    }

    @Test
    public void getProduct_whenExistingUser_thanReturnProduct() {
        ProductResponse product = productTestSteps.createProduct();

        Product createdProduct = productService.getProduct(product.getId());

        assertThat(createdProduct, notNullValue());
        assertThat(createdProduct.getId(), greaterThan(0L));
    }


    @Test
    public void getProduct_whenProductDoesNotExist_thanThrownResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.getProduct(1000));
    }

    @Test
    public void getProduct_whenMandatoryValueIsNull_thanThrownNullPointerException() {
        ProductResponse request = new ProductResponse();
        request.setId(null);
        request.setName(null);
        request.setAuthor(null);
        request.setQuantity(null);
        request.setPrice(null);

        Assertions.assertThrows(NullPointerException.class,
                () -> productService.getProduct(request.getId()));
    }



    @Test
    public void updateProduct_whenExistingProduct_thanReturnUpdatedProduct() {
        ProductResponse product = productTestSteps.createProduct();

        UpdateProductRequest request = new UpdateProductRequest();
        request.setName("Alexandru");
        request.setDescription("qwerty");
        request.setQuantity(1);
        request.setPrice(12.9);

        Product updatedProduct = productService.getProduct(product.getId());

        Product updateProduct = productService.updateProduct(updatedProduct.getId(), request);

        assertThat(updateProduct.getId(), notNullValue());
        assertThat(updateProduct.getId(), greaterThan(1L));
        assertThat(updateProduct.getName(), is(request.getName()));
        assertThat(updateProduct.getDescription(), is(request.getDescription()));
        assertThat(updateProduct.getPrice(), is(request.getPrice()));
        assertThat(updateProduct.getQuantity(), is(request.getQuantity()));
    }

    @Test
    public void updateProduct_whenValueAreDefault_thanThrownTransactionSystemException() {
        ProductResponse product = productTestSteps.createProduct();

        UpdateProductRequest request = new UpdateProductRequest();

        Product getIdProduct = productService.getProduct(product.getId());

        Assertions.assertThrows(TransactionSystemException.class,
                () -> productService.updateProduct(getIdProduct.getId(), request));
    }

    @Test
    public void updateProduct_whenProductDoesNotExist_thanThrownResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.updateProduct(1000, null));
    }

    @Test
    public void deleteProduct_whenExistingProduct_thanThrownResourceNotFoundException() {
        ProductResponse product = productTestSteps.createProduct();

        productService.deleteProduct(product.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.getProduct(product.getId()));
    }

    @Test
    public void deleteProduct_whenNotExistingProduct_thanThrownEmptyResultDataAccessException() {
        Assertions.assertThrows(EmptyResultDataAccessException.class,
                () -> productService.deleteProduct(1000));
    }
}
