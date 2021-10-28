package com.ttn.ecommerceApplication.ecommerceApplication.controller;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.ProductReviewDao;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.ProductReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductReviewController
{
    @Autowired
    ProductReviewDao productReviewDao;

    @PostMapping("/addReview/{product_id}")
    public String addProduct(@RequestBody ProductReview productReview, @PathVariable(name = "product_id") Long product_id)
    {
        productReviewDao.addReview(productReview,product_id);
        return "review added";
    }
    @GetMapping("/getReviews/{product_id}")
    public List<Object[]> getreviews(@PathVariable(name = "product_id") Long product_id)
    {
        return productReviewDao.getReviews(product_id);
    }
}
