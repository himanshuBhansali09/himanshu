package com.ttn.ecommerceApplication.ecommerceApplication.dao;
import com.ttn.ecommerceApplication.ecommerceApplication.daoImpl.UploadDaoImpl;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.AddressDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.PasswordDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.SetPasswordDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.UserDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.*;
import com.ttn.ecommerceApplication.ecommerceApplication.daoImpl.GrantAuthorityImpl;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.AlreadyExists;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.PasswordAndConfirmPasswordMismatchException;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.TokenNotFoundException;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.AddressRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.TokenRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public interface UserDao {
    public void update(AddressDTO address, Long addressId);
    public String deleteUser();
    public String addNewAddress(Address address);
    public String editUsername(UserDTO user);
    public String editEmail(UserDTO user);
    public String verifyNewEmail(String token,UserDTO user);
    public String editPassword(PasswordDTO user);
    public String deleteAddress(Long id);
    public AppUser loadUserByUsername(String username);
}