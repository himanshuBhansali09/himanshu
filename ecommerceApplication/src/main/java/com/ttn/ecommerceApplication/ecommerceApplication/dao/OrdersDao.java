package com.ttn.ecommerceApplication.ecommerceApplication.dao;

public interface OrdersDao
{

    public void placeNewOrder(Long productVariationId,int quantity, String paymentMethod,  Long AddressId);
}
