package com.ttn.ecommerceApplication.ecommerceApplication.dao;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.OrderStatus;

public interface OrderStatusDao
{
    public void updateStatus(OrderStatus orderStatus , Long productVariationId, Long orderStatusId);
}
