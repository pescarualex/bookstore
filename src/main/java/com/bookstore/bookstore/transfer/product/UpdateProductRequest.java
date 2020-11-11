package com.bookstore.bookstore.transfer.product;

import javax.validation.constraints.NotNull;

public class UpdateProductRequest {

    private Double price;
    @NotNull
    private Integer quantity;
    @NotNull
    private String name;
    private String description;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UpdateProductRequest{" +
                "price=" + price +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
