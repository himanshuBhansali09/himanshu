package com.ttn.ecommerceApplication.ecommerceApplication.dao;

import com.ttn.ecommerceApplication.ecommerceApplication.dto.AddressDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.CustomerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ProfileDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Customer;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Seller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface CustomerDao
{
    public List<Object[]> getCustomerDetails();
    public Customer getCustomer();
    public String getAnSellerAccount(Seller seller);
    public String returnRequested(Long orderStatusId);
    public String  cancelOrder(Long orderStatusId);
    String editContact(Customer customer);
    List<AddressDTO> getAddresses();
    //public ProfileDTO viewProfile();
    public String updateProfile(ProfileDTO customer);
    public List<Object[]> viewProduct(Long id);
    public List<Object[]> viewProducts(Long categoryId);
    public List<Object[]> viewSimilarProducts(Long productId);
    public ProfileDTO viewProfile(HttpServletRequest request) throws IOException;


}

