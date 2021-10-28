package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.ProductReviewDao;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Customer;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Product;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.ProductReview;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.CustomerRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.ProductRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.ProductReviewRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewDaoImpl implements ProductReviewDao
{

    @Autowired
    ProductReviewRepository productReviewRepository;

    @Autowired
    GetCurrentUser getCurrentUser;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void addReview(ProductReview productReview,Long product_id)
    {
        String username = getCurrentUser.getUser();
        Customer customer = customerRepository.findByUsername(username);
        productReview.setCustomer(customer);
        Optional<Product> product = productRepository.findById(product_id);
        productReview.setProduct(product.get());
        productReviewRepository.save(productReview);

    }

    @Override
    public List<Object[]> getReviews(Long product_id)
    {
        List<Object[]> list = productReviewRepository.getAllReviews(product_id);
        if (list.isEmpty())
        {
            throw new NullPointerException("no reviews found for this prouct");
        }
        return list;
    }
}
