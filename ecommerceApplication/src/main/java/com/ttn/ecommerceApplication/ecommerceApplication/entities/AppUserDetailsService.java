package com.ttn.ecommerceApplication.ecommerceApplication.entities;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.UserDao;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    public int count;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDao userDao;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);
        String encryptedPassword = passwordEncoder.encode("pass");
        System.out.println(passwordEncoder.encode("admi"));
        System.out.println(user.getPassword());
        System.out.println("Trying to authenticate user ::" + username);
        System.out.println("Encrypted Password ::"+encryptedPassword);
        UserDetails userDetails = userDao.loadUserByUsername(username);
        return userDetails;
    }
}