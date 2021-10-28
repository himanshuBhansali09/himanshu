package com.ttn.ecommerceApplication.ecommerceApplication.dao;

import com.ttn.ecommerceApplication.ecommerceApplication.dto.ProductDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ViewProductDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ViewProductForCustomerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Category;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Product;

import java.util.List;

public interface ProductDao
{

     Long getid(String productName);

     void save(Product product);

     public ViewProductDTO viewSingleProduct(Long productId);

     public List<ViewProductDTO> getProductDetails(Integer pageNo, Integer pageSize, String sortBy);


     public ViewProductForCustomerDTO viewSingleProductForCustomer(Long productId);

     public List<ViewProductForCustomerDTO> getProductDetailsForCustomer(Long category_id,Integer pageNo, Integer pageSize, String sortBy);



     public ViewProductForCustomerDTO viewSingleProductForAdmin(Long productId);

     public List<ViewProductForCustomerDTO> getProductDetailsForAdmin(Integer pageNo, Integer pageSize, String sortBy);


     public void deleteProduct( Long productId);
     public void editProduct(ProductDTO product, Long id) throws IllegalAccessException;
     public void addNewProduct(ProductDTO product, Long category);

     public void addProduct(Product product, Long categoryid);

     public void deactivate(Long productId);

     public void activateProduct(Long productId);

     public List<ViewProductForCustomerDTO> getSimilarProducts(Long productId,Integer pageNo, Integer pageSize, String sortBy);


     List<Object[]> viewProduct(Long product_id);
}
