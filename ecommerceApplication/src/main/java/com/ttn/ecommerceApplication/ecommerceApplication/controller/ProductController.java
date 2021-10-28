package com.ttn.ecommerceApplication.ecommerceApplication.controller;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.CategoryDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.ProductDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ProductDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ViewProductDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ViewProductForCustomerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Product;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.CategoryRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.ProductRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController
{
    @Autowired
    ProductDao productDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    //apis for seller

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for Seller to add a new product to a category")
    @PostMapping("/addProduct/{category}")
    public void addNewProduct(@Valid @RequestBody ProductDTO product, @PathVariable(name = "category") Long category) {
        productDao.addNewProduct(product,category);

    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for seller to get details of his products only those products will be shown of which seller is an owner")
    @GetMapping("/getProducts")
    public List<ViewProductDTO> getProductDetails(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                  @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                  @RequestParam(name = "sortBy", defaultValue = "ID") String sortBy)
    {
       return productDao.getProductDetails(pageNo, pageSize, sortBy);
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for seller to view a single product he owns ")
    @GetMapping("/viewSingleProduct/{productId}")
    public ViewProductDTO viewSingleProduct(@PathVariable Long productId)
    {
        return productDao.viewSingleProduct(productId);
    }


    @Secured("ROLE_SELLER")
    @ApiOperation("This api deletes a product and all its product variations")
    @DeleteMapping("/deleteAProduct/{id}")
    public void dp(@PathVariable Long id)
    {
        productDao.deleteProduct(id);
    }


    @Secured("ROLE_SELLER")
    @ApiOperation("This api is used when editing a product details")
    @PutMapping("/editProduct/{id}")
    public void editProduct(@RequestBody ProductDTO product, @PathVariable Long id) throws IllegalAccessException {
        productDao.editProduct(product,id);
    }

    //apis for customer

    @Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
    @ApiOperation("This api is for customer to view a single product")
    @GetMapping("/viewSingleProductForCustomer/{productId}")
    public ViewProductForCustomerDTO viewSingleProductForCustomer(@PathVariable Long productId)
    {
        return productDao.viewSingleProductForCustomer(productId);
    }

    @Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
    @ApiOperation("This api gives details of products of a particular category for a customer")
    @GetMapping("/getProductsForCustomer/{category_id}")
    public List<ViewProductForCustomerDTO> getProductDetails(@PathVariable Long category_id,@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                  @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                  @RequestParam(name = "sortBy", defaultValue = "ID") String sortBy)
    {
        return productDao.getProductDetailsForCustomer(category_id,pageNo, pageSize, sortBy);
    }

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("this usi is for getting similar product based on category and brand")
    @GetMapping("/getSimilarProducts/{id}")
    public List<ViewProductForCustomerDTO> getSimilarProducts(@PathVariable Long id,@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                              @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                              @RequestParam(name = "sortBy", defaultValue = "ID") String sortBy)
    {
        return productDao.getSimilarProducts(id,pageNo, pageSize, sortBy);
    }

    //apis for admin
    @ApiOperation("This api returns all the active products in database")
    @GetMapping("/getProductsForAdmin")
    public List<ViewProductForCustomerDTO> getProductDetailsForAdmin(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                             @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                             @RequestParam(name = "sortBy", defaultValue = "ID") String sortBy)
    {
        return productDao.getProductDetailsForAdmin(pageNo, pageSize, sortBy);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("This api is for admin to view single product")
    @GetMapping("/viewSingleProductForAdmin/{productId}")
    public ViewProductDTO  viewSingleProductForAdmin(@PathVariable Long productId)
    {
        return productDao.viewSingleProductForAdmin(productId);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to  deactivates a product and all its product variation")
    @PutMapping("/deactivateProduct/{productId}")
    public String deactivateProduct(@PathVariable Long productId) {
        productDao.deactivate(productId);
        return "Success";
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to activates a product")
    @PutMapping("/activateProduct/{productId}")
    public String activateProduct(@PathVariable Long productId) {
        productDao.activateProduct(productId);
        return "Success";
    }






















    @DeleteMapping("/deleteProduct/{productsId}")
    public void deleteProduct(@PathVariable Long productId)
    {
        System.out.println("1");
        productDao.deleteProduct(productId);
    }


}



