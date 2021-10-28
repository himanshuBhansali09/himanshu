package com.ttn.ecommerceApplication.ecommerceApplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.CartDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.CartDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController
{
    @Autowired
    CartDao cartDao;

    @PostMapping("/addToCart/{product_variation_id}/{quantity}")
    public String addToCart(@PathVariable("product_variation_id") Long product_variation_id,@PathVariable("quantity") int quantity)
    {
      cartDao.addToCart(product_variation_id,quantity);
      return "product added to cart successfully";
    }

    @DeleteMapping("/deletefromcart/{product_variation_id}")
    public String deleteFromCart(@PathVariable("product_variation_id") Long product_variation_id)
    {
        cartDao.deleteFromCart(product_variation_id);
        return "success";
    }

    @GetMapping("/viewCart")
    public List<CartDTO> viewCart() throws JsonProcessingException {
        return  cartDao.viewCart();
    }

    @DeleteMapping("/emptyCart")
    public String emptyCart()
    {
        cartDao.emptyCart();
        return "success";
    }

    @PostMapping("/OrderOneProductFromCart/{cartId}/{paymentMethod}/{AddressId}")
    public void placeOrderFromCart(@PathVariable("cartId") Long cartId,@PathVariable("paymentMethod") String paymentMethod,
                                   @PathVariable("AddressId") Long AddressId)
    {
        cartDao.placeOrderFromCart(cartId,paymentMethod,AddressId);

    }

    @PostMapping("/OrderWholeCart/{AddressId}")
    public void orderWholeCart(@PathVariable("AddressId") Long AddressId)
    {
        cartDao.orderWholeCart(AddressId);
    }

}
