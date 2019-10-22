package com.example.project.Mail;

import com.example.project.EncryptionUtil.EncryptionUtil;
import com.example.project.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class Mail  {

    private final EncryptionUtil encryptionUtil;
    private final JavaMailSender javaMailSender;

    @Autowired
    public Mail(EncryptionUtil encryptionUtil, JavaMailSender javaMailSender) {
        this.encryptionUtil = encryptionUtil;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(User user) throws MailException, NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(encryptionUtil.decrypt(user.getEmail()));
        mail.setFrom("vld.muri96@yahoo.com");
        mail.setText("Your acount was succesfull registered \n " +
                "username:"+ encryptionUtil.decrypt(user.getEmail())+"\n"+
                "password:"+ encryptionUtil.decrypt(user.getPassword()+"\n")+
                "Good luck!");

        javaMailSender.send(mail);
    }

}
