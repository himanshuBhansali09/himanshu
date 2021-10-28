package com.ttn.ecommerceApplication.ecommerceApplication.dao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.PasswordDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Token;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.User;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.TokenRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.NotificationService;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

public interface ForgotPasswordDao
{
    public ResponseEntity forgotPassword(String email_id);
    public ResponseEntity setPassword(String token_on_mail, PasswordDTO passwordDTO);
}