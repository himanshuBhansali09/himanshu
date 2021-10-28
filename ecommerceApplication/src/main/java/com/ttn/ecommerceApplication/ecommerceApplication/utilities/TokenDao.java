package com.ttn.ecommerceApplication.ecommerceApplication.utilities;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Token;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.User;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.TokenNotFoundException;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.TokenRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@Service
public class TokenDao {
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    @Lazy
    @Scheduled(cron = "0 0/1 * * * *")
    public void purgeExpired() {
        for (Token token : tokenRepository.findAll()) {
            Long result = System.currentTimeMillis() - token.getTimeInMill();
            if (result >= 60000)
            {
                token.setExpired(true);
                tokenRepository.save(token);
            }
        }}


    public ResponseEntity verifytokenEnteredByCustomer(String u) {
        Token token1 = null;
        for (Token token : tokenRepository.findAll()) {
            if (token.getRandomToken().equals(u)) {
                token1 = token;
            }
        }
        if (token1 == null)
        {
            throw new TokenNotFoundException("token is invalid");
        } else {
            if (token1.isExpired())
            {
                notificationService.sendNotification(userRepository.findByUsername(token1.getName()));
                tokenRepository.delete(token1);
                return ResponseEntity.ok().body("your token has been expired a new token has been sent on your mail");
            } else
                {
                System.out.println("saving");
                User user2 = userRepository.findByUsername(token1.getName());
                user2.setEnabled(true);
                user2.setActive(true);
                userRepository.save(user2);
                tokenRepository.delete(token1);
                return ResponseEntity.ok().body("your account is active you  can login now");
            }
        }
    }

    public String  getToken(User user)
    {
        Token token = new Token();
        String uu = UUID.randomUUID().toString();
        token.setRandomToken(uu);
        token.setTimeInMill(System.currentTimeMillis());
        token.setName(user.getUsername());
        tokenRepository.save(token);
        return uu;
    }




        /*for (Token token : tokens) {
            if (token.getRandomToken().equals(u)&&token.getName().equals(user1.getUsername())) {
                token1 = token;
            }
        }
        if (token1 == null)
        {

        */
            /*User user1 = null;
            char c = u.charAt(u.length() - 1);
            System.out.println(c);
            if (Character.isDigit(c)) {
                Integer i = Integer.parseInt(String.valueOf(c));
                Long l = Long.valueOf(i);
                System.out.println(l);
                Optional<User> user = userRepository.findById(l);
                if (user.isPresent()) {
                    System.out.println("your token has expired check mail for new token");
                    user1 = user.get();
                    notificationService.sendNotificaitoin(user1);
                } else {
                    System.out.println("access token you have entered is wrong");
                }
            } else {
                System.out.println("access token you have entered is wrong");
            }
        } else {
            User user1 = userRepository.findByUsername(token1.getName());
            user1.setEnabled(true);
            System.out.println(user1.getUsername() + " " + user1.isEnabled());
            userRepository.save(user1);
        }*/
       /* Long result = System.currentTimeMillis()-token1.getTimeInMill();
        System.out.println(result);
        if(result<120000)
        {
            User user1 = userRepository.findByUsername(token1.getName());
            user1.setEnabled(true);
            System.out.println(user1.getUsername()+" "+user1.isEnabled());
            userRepository.save(user1);
        }
*/

}
