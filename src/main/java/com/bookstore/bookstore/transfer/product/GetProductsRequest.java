package com.bookstore.bookstore.transfer.product;

public class GetProductsRequest {

    private String partialName;
    private Integer minQuantity;
    private Double price;
    private String author;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "GetProductsRequest{" +
                "partialName='" + partialName + '\'' +
                ", minQuantity=" + minQuantity +
                ", price=" + price +
                ", author='" + author + '\'' +
                '}';
    }
}
