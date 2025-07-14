package model.DTO;

import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
public class ReviewDTO {

    private String reviewId;
    private String userId;
    private String productId;
    private String rating;
    private String comment;
    private LocalDate createdAt;
    private boolean status;

    public ReviewDTO() {
    }

    public ReviewDTO(String reviewId, String userId, String productId, String rating, String comment, LocalDate createdAt, boolean status) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.status = status;
    }

    public ReviewDTO(String userId, String productId, String rating, String comment, LocalDate createdAt, boolean status) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.status = status;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
