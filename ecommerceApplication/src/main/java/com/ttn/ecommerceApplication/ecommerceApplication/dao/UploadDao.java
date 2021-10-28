package com.ttn.ecommerceApplication.ecommerceApplication.dao;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.Customer;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.ProductVariation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface UploadDao
{
    public ResponseEntity<Object> uploadSingleImage(MultipartFile multipartFile,Customer customer) throws IOException;

    public ResponseEntity<Object> uploadSingleImageForProductVariation(MultipartFile multipartFile, ProductVariation productVariation) throws IOException;

    public ResponseEntity<Object> uploadMultipleFiles(MultipartFile[] multipartFiles,ProductVariation productVariation) throws IOException;

    public String downloadImage(String filename, HttpServletRequest request) throws IOException;

    public String downloadImageOfProductVariation(String filename, HttpServletRequest request) throws IOException;


}
