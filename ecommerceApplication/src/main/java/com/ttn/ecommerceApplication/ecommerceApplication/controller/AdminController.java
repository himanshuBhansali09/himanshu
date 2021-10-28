package com.ttn.ecommerceApplication.ecommerceApplication.controller;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.AdminDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.RegisteredCustomersDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.RegisteredSellersDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminDao adminDao;

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "uri in which admin can view all the registered customers")
    @GetMapping("/listAllCustomers")
    public List<RegisteredCustomersDTO> getAllCustomers(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                        @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy)
    {
        return adminDao.getAllRegisteredCustomers(pageNo, pageSize, sortBy);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "uri in which admin can view all the registered sellers")
    @GetMapping("/listAllSellers")
    public List<RegisteredSellersDTO> getAllSellers(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                    @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy)
    {
        return adminDao.getAllRegisteredSellers(pageNo, pageSize, sortBy);

    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "uri in which admin can activate a user")
    @PutMapping("/activate/{user_id}")
    public ResponseEntity activateUser(@PathVariable(name = "user_id") Long user_id) {
           return adminDao.activateCustomerAndSeller(user_id);
        }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "uri in which admin can deactivate a user")
    @PutMapping("/deactivate/{user_id}")
    public ResponseEntity  deactivateUser(@PathVariable(name = "user_id") Long user_id) {
        return adminDao.deActivateCustomerAndSeller(user_id);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "uri in which admin can lock an user account")
    @PutMapping("/lock/{user_id}")
    public ResponseEntity lockUser(@PathVariable(name = "user_id") Long user_id)
    {
        return adminDao.lockUser(user_id);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "uri in which admin can unlock an user account")
    @PutMapping("/unlock/{user_id}")
    public ResponseEntity unlockUser(@PathVariable(name = "user_id") Long user_id)
    {
        return adminDao.unlockUser(user_id);
    }








}
