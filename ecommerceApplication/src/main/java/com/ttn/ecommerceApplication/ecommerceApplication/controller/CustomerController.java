package com.ttn.ecommerceApplication.ecommerceApplication.controller;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.CustomerDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.ProductDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.UploadDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.UserDao;
import com.ttn.ecommerceApplication.ecommerceApplication.daoImpl.CustomerDaoImpl;
import com.ttn.ecommerceApplication.ecommerceApplication.daoImpl.ProductDaoImpl;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.AddressDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.FullDetailDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ProfileDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Address;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Customer;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Seller;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.CustomerRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class CustomerController
{
     @Autowired
     CustomerDao customerDao;

     @Autowired
     GetCurrentUser getCurrentUser;

     @Autowired
     ProductDaoImpl productDaoImpl;

     @Autowired
     UserDao userDao;

     @Autowired
     CustomerRepository customerRepository;

     @Autowired
     UploadDao  uploadDao;

     @Autowired
     ProductDao productDao;

     @Autowired
     CustomerDaoImpl customerDaoImpl;

     @Secured("ROLE_CUSTOMER")
     @ApiOperation("uri for customer to view his profile")
     @GetMapping("/viewProfile")
     public ProfileDTO viewProfile(HttpServletRequest request) throws IOException
     {
          return customerDao.viewProfile(request);
     }

     @Secured("ROLE_CUSTOMER")
     @ApiOperation("uri for customer to view his profile image")
     @GetMapping("/viewProfileImage")
     public String viewProfileImage(HttpServletRequest request) throws IOException {
          String username = getCurrentUser.getUser();
          Customer customer = customerRepository.findByUsername(username);
          String filename = customer.getId().toString()+"_";
          return uploadDao.downloadImage(filename,request);
     }

     @ApiOperation("uri for customer to view his addressed")
     @GetMapping("/getAddresses")
     public List<AddressDTO> getAddresses()
     {
          return customerDao.getAddresses();
     }

     @Secured("ROLE_SELLER")
     @GetMapping("/getAddressesofSeller")
     public List<AddressDTO> getAddressess()
     {
          return customerDaoImpl.getAddressesofSeller();
     }


     @Secured("ROLE_CUSTOMER")
     @ApiOperation("uri for customer to update his profile")
     @PutMapping("/updateProfile")
     public String updateProfile(@RequestBody ProfileDTO customer)
     {
          return customerDao.updateProfile(customer);
     }

     @Lazy
     @ApiOperation("uri for customer to update contact details")
     @Secured("ROLE_CUSTOMER")
     @PutMapping("/editContact")
     public String  editContact(@RequestBody Customer customer)
     {
          return customerDao.editContact(customer);
     }

     @Secured("ROLE_CUSTOMER")
     @ApiOperation("uri for customer to upload his profile image")
     @PostMapping("/uploadProfilePic")
     public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException
     {
          String username = getCurrentUser.getUser();
          Customer customer = customerRepository.findByUsername(username);
          return uploadDao.uploadSingleImage(file,customer);
     }

     @Secured("ROLE_CUSTOMER")
     @ApiOperation("uri for customer to add a new address")
     @PutMapping("/addNewAddress")
     public String addNewAddress(@Valid @RequestBody Address address) {

          return userDao.addNewAddress(address);
     }

     @Secured("ROLE_CUSTOMER")
     @ApiOperation("uri for customer to delete his address")
     @DeleteMapping("/deleteAddress/{id}")
     public String deleteAddress(@PathVariable Long id)
     {
          return userDao.deleteAddress(id);
     }

     @Secured("ROLE_CUSTOMER")
     @ApiOperation("uri for customer to get a seller account")
     @PutMapping("/getSellerAccount")
     public String  getAnSellerAccount(@RequestBody Seller seller)
     {
        return customerDao.getAnSellerAccount(seller);
     }

     @Secured("ROLE_CUSTOMER")
     @GetMapping("/getAllDetailsOfProducts/{id}")
     public FullDetailDTO getAllDetails(@PathVariable Long productId){
          return productDaoImpl.getAllDetailsOfProduct(productId);
     }






















     @PostMapping("/returnRequested/{orderStatusId}")
     public String returnRequested(@PathVariable Long orderStatusId)
     { return customerDao.returnRequested(orderStatusId); }

     @PostMapping("/cancelOrder/{orderStatusId}")
     public String cancelOrder(@PathVariable Long orderStatusId)
     {
        return customerDao.cancelOrder(orderStatusId);
     }






     @GetMapping("/viewProduct/{product_id}")
     public List<Object[]> viewProduct(@PathVariable Long product_id)
     {
          return customerDao.viewProduct(product_id);
     }

     @GetMapping("/viewAllProductsForCustomer/{category_id}")
     public List<Object[]> viewAllProductsForCustomer(@PathVariable Long category_id)
     {
          return customerDao.viewProducts(category_id);
     }

     @GetMapping("/viewSimilarProduct/{product_id}")
     public List<Object[]> viewSimilarProduct(@PathVariable Long product_id)
     {
          return customerDao.viewSimilarProducts(product_id);
     }


}
