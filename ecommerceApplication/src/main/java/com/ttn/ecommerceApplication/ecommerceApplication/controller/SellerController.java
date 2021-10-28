package com.ttn.ecommerceApplication.ecommerceApplication.controller;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.SellerDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.UploadDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.SellerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.SellerProfileDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Customer;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.ProductVariation;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Seller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class SellerController
{
    @Autowired
    SellerDao sellerDao;

    @Autowired
    UploadDao uploadDao;

    @Secured("ROLE_SELLER")
    @ApiOperation("uri for seller to view his profile")
    @GetMapping("/viewProfileForSeller")
    public SellerProfileDTO viewProfile()
    {
        return sellerDao.viewProfile();
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("uri for seller to update his profile")
    @PutMapping("/updateMyProfile")
    public ResponseEntity updateMyProfile(@RequestBody SellerProfileDTO sellerProfileDTO)
    {
        return sellerDao.editMyProfile(sellerProfileDTO);
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("uri for seller to get a customer account")
    @PutMapping("/getCustomerAccount")
    public String getAnCustomerAccount(@RequestBody Customer customer)
    {
        return sellerDao.getAnCustomerAccount(customer);
    }
















    @Lazy
    @PutMapping("/editSellerDetails")
    public String updateSellerDetails(@RequestBody Seller seller) {
        return sellerDao.editSellerDetails(seller);
    }



}
