package com.ttn.ecommerceApplication.ecommerceApplication.dao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.CustomerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.SellerDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.*;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.PasswordAndConfirmPasswordMismatchException;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;


public interface RegistrationDao
{
    public String createCustomer(CustomerDTO customer);
    public ResponseEntity createSeller(SellerDTO seller);
    public ResponseEntity resendActivationLink(String emailId);

























/*
    @Lazy
    public void createCustomer(CustomerDTO user) throws InterruptedException {
        Customer user1 = modelMapper.map(user,Customer.class);
        String password = user.getPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user1.setPassword(passwordEncoder.encode(password));
        user1.setCreatedBy(user.getUsername());
        Role role = new Role();
        role.setRole("ROLE_CUSTOMER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user1.setRoles(roles);
        Set<User> users = new HashSet<>();
        users.add(user1);
        role.setUsers(users);
        userRepository.save(user1);
        if (userRepository.existsById(user1.getId())) {
            notificationService.sendNotificaitoin(user1);
        }
    }*/





}


