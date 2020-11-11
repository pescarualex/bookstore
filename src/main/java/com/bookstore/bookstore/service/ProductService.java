package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.Product;
import com.bookstore.bookstore.exception.ResourceNotFoundException;
import com.bookstore.bookstore.persistance.ProductRepository;
import com.bookstore.bookstore.transfer.product.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        LOGGER.info("Creating product: {}", request);

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setAuthor(request.getAuthor());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

        Product savedProduct = productRepository.save(product);

        return mapProductResponse(savedProduct);
    }

    public ProductResponse getProductDto(long id) {
        Product product = getProduct(id);

        return mapProductResponse(product);
    }


    public Product getProduct(long id) {
        LOGGER.info("Retrieving product: {}", id);
        return productRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Product " + id + " does not exist"));
    }

    public Page<ProductResponse> getProducts(GetProductsRequest request, Pageable pageable) {
        LOGGER.info("Retrieving products: {}", request);

        Product exampleProduct = new Product();
        exampleProduct.setName(request.getPartialName());
        exampleProduct.setQuantity(request.getMinQuantity());
        exampleProduct.setAuthor(request.getAuthor());
        exampleProduct.setPrice(request.getPrice());
        exampleProduct.setCarts(null);

        Example<Product> example = Example.of(exampleProduct,
                ExampleMatcher.matchingAny()
                        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("author", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()));

        Page<Product> productsPage = productRepository.findAll(example, pageable);

        List<ProductResponse> productsDtos = new ArrayList<>();

        for (Product product : productsPage.getContent()) {
            ProductResponse productResponse = mapProductResponse(product);
            productsDtos.add(productResponse);
        }

        return new PageImpl<>(productsDtos, pageable, productsPage.getTotalElements());
    }



    public Product updateProduct(long id, UpdateProductRequest request) {
        LOGGER.info("Updating product: {}", id);

        Product updatedProduct = getProduct(id);

        BeanUtils.copyProperties(request, updatedProduct);

        return productRepository.save(updatedProduct);
    }



    public void deleteProduct(long id) {
        LOGGER.info("Deleting product: {}", id);

        productRepository.deleteById(id);
    }


    private ProductResponse mapProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setAuthor(product.getAuthor());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setQuantity(product.getQuantity());

        return productResponse;
    }

}
