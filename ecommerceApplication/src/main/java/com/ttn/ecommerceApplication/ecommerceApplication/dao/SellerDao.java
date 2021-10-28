package com.ttn.ecommerceApplication.ecommerceApplication.dao;

import com.ttn.ecommerceApplication.ecommerceApplication.dto.SellerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.SellerProfileDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Customer;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Seller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface SellerDao
{
    public List<Object[]> getSellerDetails();
    public String getAnCustomerAccount(Customer customer);
    public String  editSellerDetails( Seller seller);
    public SellerProfileDTO viewProfile();
    public ResponseEntity editMyProfile(SellerProfileDTO sellerProfileDTO);

}
