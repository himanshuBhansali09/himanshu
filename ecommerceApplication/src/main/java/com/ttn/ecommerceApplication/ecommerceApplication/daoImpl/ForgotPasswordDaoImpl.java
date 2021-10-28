package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.ForgotPasswordDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.PasswordDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Token;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.User;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.NotFoundException;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.UserNotFoundException;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.TokenRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.NotificationService;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordDaoImpl implements ForgotPasswordDao
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    TokenRepository tokenRepository;

    @Lazy
    @Autowired
    NotificationService notificationService;

    @Autowired
    TokenDao tokenDao;

    private JavaMailSender javaMailSender;

    @Autowired
    public ForgotPasswordDaoImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public ResponseEntity forgotPassword(String email_id)
    {
        Long[] l = {};
        User user = userRepository.findByUsername(email_id);
        if (user == null) {
            throw new UserNotFoundException(messageSource.getMessage("message3.txt",l, LocaleContextHolder.getLocale()));
        } else {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(user.getUsername());
            mail.setFrom("hs631443@gmail.com");
            mail.setSubject("Regarding forgot password");
            String uu = tokenDao.getToken(user);
            mail.setText("http://localhost:3000/setPassword/"+uu);
            javaMailSender.send(mail);
            return ResponseEntity.ok().body("Token is sent to your email address");
        }
    }

    public ResponseEntity setPassword(String token_on_mail, PasswordDTO passwordDTO)
    {
        Long[] l = {};
        Token token1 = null;
        for (Token token : tokenRepository.findAll()) {
            if (token.getRandomToken().equals(token_on_mail)) {
                token1 = token;
            }
        }
        if (token1 == null) {
            throw new NotFoundException(messageSource.getMessage("message4.txt",l,LocaleContextHolder.getLocale()));
        } else {
            if (token1.isExpired()) {
                notificationService.sendNotification(userRepository.findByUsername(token1.getName()));
                tokenRepository.delete(token1);
                return ResponseEntity.ok().body("token has been expired check email for new token");
            }
            else {
                User user2 = userRepository.findByUsername(token1.getName());
                user2.setPassword(new BCryptPasswordEncoder().encode(passwordDTO.getPassword()));
                userRepository.save(user2);
                tokenRepository.delete(token1);
                return ResponseEntity.ok().body("password has been successfully changed");
            }
        }


    }
}
