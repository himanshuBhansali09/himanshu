package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.UserDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.AddressDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.PasswordDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.SetPasswordDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.UserDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.*;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.*;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.AddressRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.TokenRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserDaoImpl implements UserDao
{
    public int count;
    Long[] l = {};

    @Autowired
    UserRepository userRepository;

    @Autowired
    GetCurrentUser getCurrentUser;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    ModelMapper modelMapper;

    private JavaMailSender javaMailSender;

    @Autowired
    public UserDaoImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Lazy
    @Autowired
    NotificationService notificationService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UploadDaoImpl uploadDao;

    public void update(AddressDTO address, Long addressId) {
        String username = getCurrentUser.getUser();
        User user = userRepository.findByUsername(username);
        Set<Address> addresses = user.getAddresses();
        Optional<Address> address1 = addressRepository.findById(addressId);
        int count=0;
        if (address1.isPresent())
        {
            for (Address address2 : addresses) {
                if (address1.get().getId() == address2.getId()) {
                    if (address.getAddressLine() != null)
                        address2.setAddressLine(address.getAddressLine());
                    if (address.getCity() != null)
                        address2.setCity(address.getCity());
                    if (address.getCountry() != null)
                        address2.setCountry(address.getCountry());
                    if (address.getState() != null)
                        address2.setState(address.getState());
                    if (address.getZipcode() != null)
                        address2.setZipcode(address.getZipcode());
                    address2.setUser(user);
                    address2.setId(addressId);
                    address2.setModifiedBy(username);
                    addressRepository.save(address2);
                    count++;
                }
            }
            if (count==0)
            {
                throw new NullException("you cannot update this address");
            }
        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l, LocaleContextHolder.getLocale()));
        }

    }

    public String deleteUser() {
        String username = getCurrentUser.getUser();
        User user = userRepository.findByUsername(username);
        userRepository.deleteUser(user.getId());
        return "success";
    }

    public String addNewAddress(Address address) {
        String username = getCurrentUser.getUser();
        User user = userRepository.findByUsername(username);
        address.setLabel("home");
        address.setUser(user);
        user.addAddress(address);
        user.setModifiedBy(username);
        userRepository.save(user);
        return "success";
    }

    public String editUsername(UserDTO user)
    {
        User user1 = userRepository.findByUsername(user.getUsername());
        if (user1 == null) {
            String username = getCurrentUser.getUser();
            User user2 = userRepository.findByUsername(username);
            user2.setUsername(user.getUsername());
            user2.setModifiedBy(username);
            userRepository.save(user2);
            return "Success";
        }
        else
            {
            throw new AlreadyExists("username is occupied,try with a different username");
        }
    }

    //to do
    @Lazy
    public String editEmail(UserDTO user)
    {
        User user1 = modelMapper.map(user, User.class);
        user1.setUsername(user.getUsername());
        notificationService.sendNotification(user1);
        return "success";
    }

    @Lazy
    public String verifyNewEmail(String token,UserDTO user)
    {
        Token token1 = null;
        for (Token token2 : tokenRepository.findAll())
        {
            if (token2.getRandomToken().equals(token))
            {
                token1 = token2;
            }
        }
        if (token1 == null)
        {
            throw new TokenNotFoundException("token is invalid");
        } else
            {
            if (token1.isExpired())
            {
                User user1 = modelMapper.map(user, User.class);
                String username = getCurrentUser.getUser();
                user1.setUsername(username);
                notificationService.sendNotification(user1);
                tokenRepository.delete(token1);
                throw new TokenNotFoundException("token is expired check mail for new token");
            } else
                {
                System.out.println("saving");
                String username = getCurrentUser.getUser();
                User user2 = userRepository.findByUsername(username);
                user2.setUsername(user.getUsername());
                user2.setModifiedBy(user2.getUsername());
                userRepository.save(user2);
                tokenRepository.delete(token1);
                return "success";
            }
        }
    }

    public String editPassword(PasswordDTO user) {
        String username = getCurrentUser.getUser();
        User user1 = userRepository.findByUsername(username);
        if (user.getPassword() != null && user.getConfirmPassword() != null) {
            if (user.getPassword().equals(user.getConfirmPassword())) {
                user1.setPassword(passwordEncoder.encode(user.getPassword()));
                user1.setModifiedBy(username);
                userRepository.save(user1);
                notificationService.sendToSeller(user1, "password changed status", "your password has been successfully changed");
            }else {
                throw new PasswordAndConfirmPasswordMismatchException("password and confirm password does not match");
            }
        } else {
            throw new NullPointerException("password and confirm password both are mandatory");
        }
        return "success";
    }

    @Modifying
    @Transactional
    public String deleteAddress(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            userRepository.deleteAddress(id);
        } else {
            throw new NotFoundException("address not present");
        }
          return "successfully deleted";
    }

















    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        Set<Role> roles = user.getRoles();
        Iterator<Role> roleIterator = roles.iterator();
        String ro = null;
        List<GrantAuthorityImpl> list = new ArrayList<>();
        while (roleIterator.hasNext())
        {
            Role role = roleIterator.next();
            list.add(new GrantAuthorityImpl(role.getRole()));
        }
        System.out.println(user);

        if (username != null) {
            return new AppUser(user.getUsername(), user.getPassword(), list/*Arrays.asList(new GrantAuthorityImpl(ro))*/,user.isEnabled(),user.isAccountNonLocked(),user.isAccountNonExpired());
        } else {
            throw new RuntimeException();
        }
    }
}
