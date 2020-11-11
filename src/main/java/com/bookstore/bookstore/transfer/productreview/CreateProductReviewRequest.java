package com.bookstore.bookstore.transfer.productreview;

public class CreateProductReviewRequest {

    private String content;
    private long productId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "CreateProductReviewRequest{" +
                "content='" + content + '\'' +
                ", productId=" + productId +
                '}';
    }
}
