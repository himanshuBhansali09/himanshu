package com.ttn.ecommerceApplication.ecommerceApplication.utilities;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.User;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Lazy
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TokenDao tokenDao;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Lazy
    @Autowired
    NotificationService notificationService;

    @Async
    public void sendNotification(User user) throws MailException {
        System.out.println("Sending email...");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getUsername());
        mail.setFrom("hs631443@gmail.com");
        mail.setSubject("unique token for activation of email");
        String uu = tokenDao.getToken(user);
        mail.setText("http://localhost:3000/verify/"+uu);
        javaMailSender.send(mail);
        System.out.println("Email Sent!");
    }

    @Async
    public void sendToAdmin(User user,String text) throws MailException
    {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getUsername());
        mail.setFrom("hs631443@gmail.com");
        mail.setSubject("Please activate the product");
        mail.setText(text);
        javaMailSender.send(mail);
    }

    @Async
    public void sendToSeller(User seller, String subject, String text)throws MailException
    {
        System.out.println("Sending email...");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(seller.getUsername());
        mail.setFrom("hs631443@gmail.com");
        mail.setSubject(subject);
        mail.setText(text);
        javaMailSender.send(mail);
    }







}
