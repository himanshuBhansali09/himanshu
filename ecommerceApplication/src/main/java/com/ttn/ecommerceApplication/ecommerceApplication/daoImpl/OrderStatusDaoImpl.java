package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.OrderStatusDao;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.OrderStatus;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Product;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Seller;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.OrderStatusRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.ProductRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.ProductVariationRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.SellerRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderStatusDaoImpl implements OrderStatusDao
{
        @Autowired
        GetCurrentUser getCurrentUser;
        @Autowired
        SellerRepository sellerRepository;
        @Autowired
        ProductVariationRepository productVariationRepository;

        @Autowired
        ProductRepository productRepository;

        @Autowired
        OrderStatusRepository orderStatusRepository;
        @Override
        public void updateStatus(OrderStatus orderStatus, Long productVariationId, Long orderStatusId) {
            String username = getCurrentUser.getUser();
            Seller seller = sellerRepository.findByUsername(username);
            Long productId = productVariationRepository.getProductId(productVariationId);
            Optional<Product> productOptional = productRepository.findById(productId);
            Product product1 = productOptional.get();
            if ((product1.getSeller().getUsername()).equals(seller.getUsername())) {
                Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(orderStatusId);
                OrderStatus orderStatus1 = orderStatusOptional.get();
                orderStatus1.setFromStatus(orderStatus.getFromStatus());
                orderStatus1.setToStatus(orderStatus.getToStatus());
                orderStatus1.setTransitionNotesComments(orderStatus.getTransitionNotesComments());
                orderStatusRepository.save(orderStatus1);
            } else {
                throw new NullPointerException("you cannot set status of this product");
            }
        }

    }

