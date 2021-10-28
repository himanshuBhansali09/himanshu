package com.ttn.ecommerceApplication.ecommerceApplication.controller;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.OrdersDao;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.*;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.*;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class OrdersController
{
    @Autowired
    GetCurrentUser getCurrentUser;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrdersDao orderDao;

    @PostMapping("/placeOrder/{productVariationId}/{quantity}/{paymentMethod}/{AddressId}")
    public void placeOrder(@PathVariable Long productVariationId,@PathVariable int quantity,
                           @PathVariable String paymentMethod,@PathVariable Long AddressId)
    {

        orderDao.placeNewOrder(productVariationId,quantity,paymentMethod,AddressId);
    }

    @GetMapping("/showOrderHistory")
    public List<Object[]> getOrderHistory()
    {
        String username = getCurrentUser.getUser();
        Customer customer =customerRepository.findByUsername(username);
        List<Object[]> objects= ordersRepository.getAllOrders(customer.getId());
        return objects;
    }
}
