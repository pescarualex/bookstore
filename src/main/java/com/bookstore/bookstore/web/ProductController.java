package com.bookstore.bookstore.web;

import com.bookstore.bookstore.domain.Product;
import com.bookstore.bookstore.service.CartService;
import com.bookstore.bookstore.service.ProductService;
import com.bookstore.bookstore.transfer.product.CreateProductRequest;
import com.bookstore.bookstore.transfer.product.GetProductsRequest;
import com.bookstore.bookstore.transfer.product.ProductResponse;
import com.bookstore.bookstore.transfer.product.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request) {
        ProductResponse product = productService.createProduct(request);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long id) {
        ProductResponse product = productService.getProductDto(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(GetProductsRequest request, Pageable pageable) {
        Page<ProductResponse> products = productService.getProducts(request, pageable);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/admin/products")
    public ResponseEntity<Page<ProductResponse>> getProductsForAdminPage(GetProductsRequest request, Pageable pageable) {
        Page<ProductResponse> products = productService.getProducts(request, pageable);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody @Valid UpdateProductRequest request) {
        Product product = productService.updateProduct(id, request);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
