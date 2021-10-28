package com.ttn.ecommerceApplication.ecommerceApplication.controller;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.OrderStatusDao;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderStatusController {

    @Autowired
    OrderStatusDao orderStatusDao;

    @PostMapping("/setStatus/{productVariationId}/{orderStatusId}")
    public void setStatus(@RequestBody OrderStatus orderStatus ,
                          @PathVariable Long productVariationId, @PathVariable Long orderStatusId) {

        orderStatusDao.updateStatus(orderStatus,productVariationId,orderStatusId);
    }
}