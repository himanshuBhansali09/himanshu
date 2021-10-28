package com.ttn.ecommerceApplication.ecommerceApplication.dao;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.ProductReview;

import java.util.List;

public interface ProductReviewDao
{
    public void addReview(ProductReview productReview,Long product_id);

    public List<Object[]> getReviews(Long product_id);
}
