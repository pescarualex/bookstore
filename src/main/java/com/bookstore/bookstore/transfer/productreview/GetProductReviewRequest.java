package com.bookstore.bookstore.transfer.productreview;

public class GetProductReviewRequest {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "GetProductReviewRequest{" +
                "content='" + content + '\'' +
                '}';
    }
}
