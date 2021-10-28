package com.ttn.ecommerceApplication.ecommerceApplication.repository;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.ProductReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductReviewRepository extends CrudRepository<ProductReview,Long>
{
    @Query(value = "select review,rating from product_review where product_id=:product_id",nativeQuery = true)
    List<Object[]> getAllReviews(@Param("product_id") Long product_id);
}
