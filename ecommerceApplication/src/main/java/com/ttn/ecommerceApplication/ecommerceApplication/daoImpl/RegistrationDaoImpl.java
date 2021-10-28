package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.RegistrationDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.CustomerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.SellerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.*;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.NotFoundException;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.NullException;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.PasswordAndConfirmPasswordMismatchException;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.UserNotFoundException;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.AddressRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.TokenRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import java.util.Set;

@Service
public class RegistrationDaoImpl implements RegistrationDao
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TokenRepository tokenRepository;

    private JavaMailSender javaMailSender;

    @Autowired
    public RegistrationDaoImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Lazy
    @Autowired
    NotificationService notificationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MessageSource messageSource;


    public String createCustomer(CustomerDTO customer) {

        if(customer.getPassword().equals(customer.getConfirmPassword()))
        {
            Customer customer1= modelMapper.map(customer,Customer.class);
            String password = customer.getPassword();
            customer1.setPassword(new BCryptPasswordEncoder().encode(password));
            customer1.addRoles(new Role("ROLE_CUSTOMER"));
            customer1.setActive(false);
            customer1.setCreatedBy(customer1.getUsername());
            userRepository.save(customer1);
            if ( userRepository.existsById(customer1.getId()))
            {
                notificationService.sendNotification(customer1);
            }
            return "success";
        }
        else
        {
            Long[] l = {};
            throw new NullException(messageSource.getMessage("message23.txt",l,LocaleContextHolder.getLocale()));
        }

    }

    public ResponseEntity createSeller(SellerDTO seller)
    {
        if (seller.getPassword().equals(seller.getConfirmPassword())) {
            Seller seller1 = modelMapper.map(seller, Seller.class);
            String password = seller.getPassword();
            seller1.setPassword(passwordEncoder.encode(password));
            seller1.addRoles(new Role("ROLE_SELLER"));
            seller1.setActive(false);
            seller1.setCreatedBy(seller.getUsername());
            Address address = modelMapper.map(seller,Address.class);
            address.setLabel("office");
            Set<Address>  addresses = new HashSet<>();
            address.setUser(seller1);
            addresses.add(address);
            seller1.setAddresses(addresses);
            userRepository.save(seller1);
            if (userRepository.existsById(seller1.getId()))
            {
                notificationService.sendToSeller(seller1,"Regarding account activation","you account has been created you can access it once admin verifies it");

            }
            return ResponseEntity.ok().body("successfully registered");

        }
        else {
            throw  new PasswordAndConfirmPasswordMismatchException("Both passwords are not same");
        }

    }

    public ResponseEntity resendActivationLink(String emailId)
    {
        User user = userRepository.findByUsername(emailId);
        if (user==null)
        {
            throw new UserNotFoundException("user with this email id is not present");
        }
        else
            {
                for (Token token : tokenRepository.findAll())
                {
                    if (token.getName().equals(user.getUsername()))
                    {
                        tokenRepository.delete(token);
                    }
                }
                if (user.isEnabled()==false&&user.isActive()==false)
                notificationService.sendNotification(user);
                else
                 throw new NullException("account is already active");
                return ResponseEntity.ok().body("activation token sent to the given email address");
            }
         }
}
