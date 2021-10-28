package com.ttn.ecommerceApplication.ecommerceApplication.dao;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.User;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class VerifyNumber
{
    @Autowired
    UserRepository userRepository;

    public void verifyUserPhoneNumber(Long user_id)
    {
        Optional<User> user = userRepository.findById(user_id);
        User user1 = user.get();
    }
}
