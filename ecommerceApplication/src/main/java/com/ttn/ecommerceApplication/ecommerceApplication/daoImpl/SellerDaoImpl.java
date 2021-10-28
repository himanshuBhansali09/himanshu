package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.SellerDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.SellerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.SellerProfileDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.*;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.NullException;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.PatternMismatchException;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.AddressRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.CustomerRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.SellerRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SellerDaoImpl implements SellerDao {
    @Autowired
    GetCurrentUser getCurrentUser;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    AddressRepository addressRepository;

    public SellerProfileDTO viewProfile() {
        String username = getCurrentUser.getUser();
        Seller seller = sellerRepository.findByUsername(username);
        SellerProfileDTO sellerProfileDTO = new SellerProfileDTO(seller.getId(), seller.getFirstName(), seller.getLastName(), seller.getisActive(), seller.getCompanyContactNo(), seller.getCompanyName(), seller.getGstNo(), seller.getMiddleName());
        Set<Address> address = seller.getAddresses();
        for (Address address1 : address) {
            sellerProfileDTO.setCity(address1.getCity());
            sellerProfileDTO.setAddressLine(address1.getAddressLine());
            sellerProfileDTO.setCountry(address1.getCountry());
            sellerProfileDTO.setState(address1.getState());
            sellerProfileDTO.setZipCode(address1.getZipcode());
        }
        return sellerProfileDTO;

    }

    public ResponseEntity editMyProfile(SellerProfileDTO sellerProfileDTO) {
        String username = getCurrentUser.getUser();
        Seller seller = sellerRepository.findByUsername(username);
        if (sellerProfileDTO.getFirstName() != null)
            seller.setFirstName(sellerProfileDTO.getFirstName());
        if (sellerProfileDTO.getMiddleName() != null)
            seller.setMiddleName(sellerProfileDTO.getMiddleName());
        if (sellerProfileDTO.getLastName() != null)
            seller.setLastName(sellerProfileDTO.getLastName());
        if (sellerProfileDTO.getIs_Active() != null)
            seller.setActive(sellerProfileDTO.getIs_Active());
        if (sellerProfileDTO.getCompanyContact() != null) {
            if (sellerProfileDTO.getCompanyContact().matches("(\\+91|0)[0-9]{10}")) {
                seller.setCompanyContactNo(sellerProfileDTO.getCompanyContact());
            } else {
                throw new PatternMismatchException("contact number is not correct");
            }
        }

        if (sellerProfileDTO.getCompanyName() != null)
            seller.setCompanyName(sellerProfileDTO.getCompanyName());
        if (sellerProfileDTO.getGST() != null) {
            if (sellerProfileDTO.getGST().matches("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")) {
                seller.setGstNo(sellerProfileDTO.getGST());
            } else {
                throw new PatternMismatchException("gst number is not correct");
            }
        }
        sellerRepository.save(seller);

        return ResponseEntity.ok().body("profile is updated successfully");
    }











    @Override
    public String editSellerDetails(Seller seller) {
        String user = getCurrentUser.getUser();
        Seller user1 = sellerRepository.findByUsername(user);
        if (seller.getCompanyName() != null) {
            user1.setCompanyName(seller.getCompanyName());
        }
        if (seller.getCompanyContactNo() != null) {
            if (seller.getCompanyContactNo().matches("(\\+91|0)[0-9]{10}")) {
                user1.setCompanyContactNo(seller.getCompanyContactNo());
            } else {
                throw new PatternMismatchException("Contact number should start with +91 or 0 and length should be 10");
            }
        }
        if (seller.getGstNo() != null) {

            if (seller.getGstNo().matches("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")) {
                user1.setGstNo(seller.getGstNo());
            } else {
                throw new PatternMismatchException("gst number is not correct");
            }
        }
        user1.setModifiedBy(user);
        sellerRepository.save(user1);
        return "success";
    }




    @Override
    public List<Object[]> getSellerDetails() {
        String username = getCurrentUser.getUser();
        List<Object[]> objects = sellerRepository.getDetails(username);
        return objects;
    }

    @Transactional
    @Override
    public String getAnCustomerAccount(Customer customer) {
        if (customer.getContactNo() != null) {
            if (customer.getContactNo().matches("(\\+91|0)[0-9]{10}")) {
                String username = getCurrentUser.getUser();
                User seller = userRepository.findByUsername(username);
                customer.setId(seller.getId());
                customerRepository.insertIntoCustomer(customer.getContactNo(), customer.getId());
                Set<Role> roles = seller.getRoles();
                Role role = new Role();
                role.setRole("ROLE_CUSTOMER");
                roles.add(role);
                seller.setRoles(roles);
                Set<User> users = new HashSet<>();
                role.setUsers(users);
                userRepository.save(seller);
                return "success";
            } else {
                throw new PatternMismatchException("Contact number should start with +91 or 0 and length should be 10");
            }
        } else {
            throw new NullException("contact number is mandatory");
        }
    }



}
