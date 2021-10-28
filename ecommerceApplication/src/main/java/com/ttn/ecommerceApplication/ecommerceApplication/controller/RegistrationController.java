package com.ttn.ecommerceApplication.ecommerceApplication.controller;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.CustomerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.SellerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.NotificationService;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.RegistrationDao;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.TokenDao;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.User;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.RoleRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    private TokenStore tokenStore;


    @Autowired
    RegistrationDao registrationDao;

    @Autowired
    TokenDao tokenDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private NotificationService notificationService;

    @Lazy
    @ApiOperation("Uri for customer registration")
    @PostMapping("/registerAsCustomer")
    public String createCustomer(@Valid @RequestBody CustomerDTO user) throws InterruptedException
    {
        return registrationDao.createCustomer(user);
    }

    @ApiOperation("uri for activating user after which user can login into application")
    @PutMapping("/activateCustomer/{token}")
    public ResponseEntity verifyUser(@PathVariable(name = "token") String token)
    {
        return tokenDao.verifytokenEnteredByCustomer(token);
    }

    @ApiOperation("uri for resending activation link for customer")
    @PostMapping("/resendActivationLink/{mailId}")
    public ResponseEntity resendActivationLink(@PathVariable(name = "mailId") String mailId)
    {
        return registrationDao.resendActivationLink(mailId);
    }


    @ApiOperation("Uri for seller Registration")
    @PostMapping("/registerAsSeller")
    public ResponseEntity createSeller(@Valid @RequestBody SellerDTO user) throws InterruptedException {
        return registrationDao.createSeller(user);
    }



    @ApiOperation("uri for log out")
    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
            System.out.println("username is "+username);
        } else {
             username = principal.toString();
            System.out.println("username is "+ username);
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }



















































/*
    @GetMapping("/listofvendors")
    public List<Object[]> find()
    {
        List<Object[]> objects = userRepository.findAllVendors();
        return objects;
    }
    @GetMapping("/listofbuyers")
    public List<Object[]> findBuyers()
    {
        List<Object[]> objects = userRepository.findAllBuyers();
        return objects;
    }*/

  /*  @Transactional
    @DeleteMapping("/deleteUser/{user_id}")
    public void deleteUserById(@PathVariable(name = "user_id")  int user_id)
    {
        List<Object[]> list = userRepository.findRoleIdFromMappingTable(user_id);
        userRepository.deleteFromUserRole(user_id);
        userRepository.deleteById(user_id);
        for (Object[] objects : list)
        {
            int a = (int) objects[0];
            roleRepository.deleteById(a);
        }
    }

  */  @GetMapping("/customer/home")
    public String vendorHome() {
        return "Vendor Home";
    }

    @GetMapping("/buyer/home")
    public String BuyerHome() {
        return "Buyer Home";
    }

    //admin home page
    @GetMapping("/admin/home")
    public String adminHome() {
        return "Admin home";
    }

    @GetMapping("/seller/home")
    public String userHome() {
        return "User home";
    }

}
