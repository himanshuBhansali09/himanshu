package com.ttn.ecommerceApplication.ecommerceApplication.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.CartDTO;

import java.util.List;

public interface CartDao
{
    public void addToCart(Long product_variation_id,int quantity);

    public void deleteFromCart(Long product_variation_id);

    public void emptyCart();

    public List<CartDTO> viewCart() throws JsonProcessingException;

    public void placeOrderFromCart(Long cartId,String paymentMethod,Long AddressId);

    public void orderWholeCart( Long AddressId);


}
