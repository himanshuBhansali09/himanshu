package com.ttn.ecommerceApplication.ecommerceApplication.controller;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.UploadDao;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UploadController
{
    @Autowired
    UploadDao uploadDao;
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, Customer customer) throws IOException
    {
        return uploadDao.uploadSingleImage(file,customer);
    }

    @GetMapping("/download/{fileName:.+}")
    public String downloadFileFromLocal(@PathVariable String fileName, HttpServletRequest request) throws IOException
    {
        return uploadDao.downloadImage(fileName,request);
    }
}
