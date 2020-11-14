package com.bookstore.bookstore.persistance;

import com.bookstore.bookstore.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
