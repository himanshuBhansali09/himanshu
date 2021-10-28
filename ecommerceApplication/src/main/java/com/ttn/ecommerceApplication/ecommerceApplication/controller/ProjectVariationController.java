package com.ttn.ecommerceApplication.ecommerceApplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.ProductDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.ProductVariationDao;
import com.ttn.ecommerceApplication.ecommerceApplication.daoImpl.UploadDaoImpl;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ProductVariationDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.ProductVariation;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.ProductRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.ProductVariationRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class ProjectVariationController
{
    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductVariationDao productVariationDao;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UploadDaoImpl uploadDao;

    @Secured("ROLE_SELLER")
    @ApiOperation("uri for seller to add a product variation")
    @PostMapping("/addProductVariations/{productId}")
    public void addNewProductVariation(@Valid @RequestBody ProductVariation productVariation, @PathVariable Long productId) throws JsonProcessingException {

        productVariationDao.addNewProductVariation(productVariation, productId);
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("uri for seller to view a single product variation")
    @GetMapping("/viewSingleProductVariation/{productVariationId}")
    public ProductVariationDTO getSingleProductVariation(@PathVariable Long productVariationId) throws JsonProcessingException {

        return productVariationDao.getSingleProductVariation(productVariationId);
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("uri for seller to get all product variation of a product")
    @GetMapping("/getAllProductVariations/{productId}")
    public List<ProductVariationDTO> getAllProductVariations(@PathVariable Long productId) throws JsonProcessingException {
        return productVariationDao.getAllProductVariations(productId);
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("uri for seller to edit product Variation")
    @PostMapping("/editProductVariations/{productVariationId}")
    public void updateProductVariation(@RequestBody ProductVariation productVariation, @PathVariable Long productVariationId) throws JsonProcessingException {

        productVariationDao.editProductVariation(productVariation,productVariationId);
    }

    @ApiOperation("uri for viewing image of product variation")
    @GetMapping("/viewProductVariationImage/{filename}")
    public String  viewProfileImage(@PathVariable String filename, HttpServletRequest request) throws IOException {
        return uploadDao.downloadImageOfProductVariation(filename,request);
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("uri to delete a product variation")
    @DeleteMapping("/deleteProductVariation/{productVariationId}")
    public String  deleteProductVariation(@PathVariable Long productVariationId) {
        return productVariationDao.removeProductVariation(productVariationId);
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("uri for customer to upload pics for productVariation")
    @PostMapping("/uploadMultipleImagesForProductVariation/{id}")
    public ResponseEntity<Object> uploadFiles(@PathVariable Long id,@RequestParam("files") MultipartFile[] files, ProductVariation productVariation) throws IOException
    {
        return uploadDao.uploadMultipleFiles(files,productVariation);
    }

    @Secured("ROLE_SELLER")
    @PostMapping("/uploadVariationPic/{id}")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable Long id) throws IOException
    {
        Optional<ProductVariation> productVariation = productVariationRepository.findById(id);
        if (productVariation.isPresent())
        {
           return uploadDao.uploadSingleImageForProductVariation(file,productVariation.get());
        }
        else
        {
            throw new RuntimeException();
        }
    }

    @Secured("ROLE_CUSTOMER")
    @GetMapping("/getAllImagesOfAProductVariation/{id}")
    public List<String> getAllImagesOfAProductVariation(@PathVariable Long id)
    {
        return productVariationDao.allImagesOfAProductVariation(id);
    }

}
