package com.bookstore.bookstore.transfer.productreview;

public class ProductReviewRequest {

    private long id;
    private long content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContent() {
        return content;
    }

    public void setContent(long content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ProductReviewRequest{" +
                "id=" + id +
                ", content=" + content +
                '}';
    }
}
