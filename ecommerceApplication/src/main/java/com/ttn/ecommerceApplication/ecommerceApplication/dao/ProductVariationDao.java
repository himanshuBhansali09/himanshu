package com.ttn.ecommerceApplication.ecommerceApplication.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ProductVariationDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.ProductVariation;

import java.util.List;

public interface ProductVariationDao
{
    public void makeProductVariationNotAvailable(ProductVariation productVariation);
    public void updateQuantity(Long productVariationId, int quantity);
    public void editProductVariation(ProductVariation productVariation, Long productVariationId) throws JsonProcessingException;
    public String removeProductVariation(Long productVariationId);
    public void addNewProductVariation(ProductVariation productVariation, Long productId) throws JsonProcessingException;
    public ProductVariationDTO getSingleProductVariation(Long productVariationId) throws JsonProcessingException;
    public List<ProductVariationDTO> getAllProductVariations(Long productId) throws JsonProcessingException;
    public List<String> allImagesOfAProductVariation(Long productVariationId);



}
