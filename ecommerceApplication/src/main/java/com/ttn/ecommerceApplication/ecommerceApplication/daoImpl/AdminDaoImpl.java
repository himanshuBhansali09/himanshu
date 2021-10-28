package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.AdminDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.AddressDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.RegisteredCustomersDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.RegisteredSellersDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Address;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.User;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.UserNotFoundException;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.ProductRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class AdminDaoImpl implements AdminDao
{
    private JavaMailSender javaMailSender;

    @Autowired
    public AdminDaoImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    UserRepository userRepository;

    @Lazy
    @Autowired
    NotificationService notificationService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    ModelMapper modelMapper;


    public List<RegisteredCustomersDTO> getAllRegisteredCustomers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));

        List<RegisteredCustomersDTO> list = new ArrayList<>();
        for (Long l : userRepository.findIdOfCustomers(paging))
        {
            Optional<User> user1 = userRepository.findById(l);
            User user = user1.get();
            if (user.getId()==l) {
                RegisteredCustomersDTO registeredCustomersDTO = modelMapper.map(user,RegisteredCustomersDTO.class);
                list.add(registeredCustomersDTO);
            }
        }

        return list;
    }

    public List<RegisteredSellersDTO> getAllRegisteredSellers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<RegisteredSellersDTO> list = new ArrayList<>();
        for (Long l : userRepository.findIdOfSellers(paging))
        {
            Optional<User> user1 = userRepository.findById(l);
            User user = user1.get();
            if (user.getId()==l)
            {
                RegisteredSellersDTO registeredSellersDTO = modelMapper.map(user,RegisteredSellersDTO.class);
                AddressDTO addressDTO = new AddressDTO();
                for (Address address : user.getAddresses())
                {
                    addressDTO = modelMapper.map(address,AddressDTO.class);
                }
                registeredSellersDTO.setAddressDTO(addressDTO);
                list.add(registeredSellersDTO);
            }
        }

        return list;
    }


    public ResponseEntity activateCustomerAndSeller(Long id)
    {
        User user1 = null;
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent())
        {
            user1 = user.get();
            if (user1.isEnabled()==true)
            {
                return ResponseEntity.ok().body("user is already activated");
            }
            else
            {
                user1.setEnabled(true);
                user1.setActive(true);
                userRepository.save(user1);
                notificationService.sendToSeller(user1,"Regarding account activation","your account has been activated by admin you can now login");
                return ResponseEntity.ok().body("account has been activated");
            }
        }
        else
        {
            Long[] l ={};
            throw new UserNotFoundException(messageSource.getMessage("message3.txt",l, LocaleContextHolder.getLocale()));
        }

    }

    public ResponseEntity  deActivateCustomerAndSeller(Long id)
    {
        User user1 = null;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
        {
            user1 = user.get();
            if (user1.isEnabled()==false)
            {
                return ResponseEntity.ok().body("user account is already deactivated");
            }
            else
            {
                user1.setEnabled(false);
                user1.setActive(false);
                userRepository.save(user1);
                notificationService.sendToSeller(user1,"Regarding account deactivation","your account has been deactivated by admin you can not login now");
                return ResponseEntity.ok().body("account has been successfully deactivated");
            }
        }
        else
        {
            Long[] l ={};
            throw new UserNotFoundException(messageSource.getMessage("message3.txt",l, LocaleContextHolder.getLocale()));

        }

    }

    public ResponseEntity lockUser(Long id)
    {
        User user1 = null;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
        {
            user1 = user.get();
            if (user1.isAccountNonLocked()==false)
            {
                return ResponseEntity.ok().body("user account is already locked");
            }
            else
            {
                user1.setAccountNonLocked(false);
                userRepository.save(user1);
                notificationService.sendToSeller(user1,"regarding account","your account has been locked by admin");
                return ResponseEntity.ok().body("account has been locked");
            }
        }
        else
        {
            Long[] l ={};
            throw new UserNotFoundException(messageSource.getMessage("message3.txt",l, LocaleContextHolder.getLocale()));

        }

    }

    public ResponseEntity unlockUser(Long id)
    {
        User user1 = null;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
        {
            user1 = user.get();
            if (user1.isAccountNonLocked()==true)
            {
                return ResponseEntity.ok().body("user account is already unlocked");
            }
            else
            {
                user1.setAccountNonLocked(true);
                userRepository.save(user1);
               notificationService.sendToSeller(user1,"regarding account","your account has been unlocked by admin");
                return ResponseEntity.ok().body("account has been unlocked");
            }
        }
        else
        {
            Long[] l ={};
            throw new UserNotFoundException(messageSource.getMessage("message3.txt",l, LocaleContextHolder.getLocale()));

        }
    }



}
