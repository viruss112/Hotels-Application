package com.example.project.Mail;

import com.example.project.EncryptionUtil.EncryptionUtil;
import com.example.project.Hotel.Hotel;
import com.example.project.Reservation.Reservation;
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

    private static EncryptionUtil encryptionUtil ;
    private static JavaMailSender javaMailSender ;

    @Autowired
    public Mail(EncryptionUtil encryptionUtil, JavaMailSender javaMailSender) {
        this.encryptionUtil = encryptionUtil;
        this.javaMailSender = javaMailSender;
    }


    public static <T> void sendEmail(T scope, User user) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SimpleMailMessage mail = new SimpleMailMessage();


        mail.setTo(encryptionUtil.decrypt(user.getEmail()));
        mail.setFrom("vld.muri96@yahoo.com");
        if(scope instanceof User){
            mail.setSubject("Registration email");
            mail.setText("Your acount was succesfull registered \n " +
                    "username:"+ encryptionUtil.decrypt(user.getEmail())+"\n"+
                    "password:"+ encryptionUtil.decrypt(user.getPassword())+"\n"+
                    "Good luck!");
        }
        else
            if(scope instanceof Reservation){
                mail.setSubject("Booking email");
                mail.setText("You booked a "+((Reservation) scope).getRoomType()+" room"+" from "+((Reservation) scope).getReservationFromDate()+" to "+((Reservation) scope).getReservationToDate()+
                        "at " + ((Reservation) scope).getHotel().getHotelName());

            }


        javaMailSender.send(mail);

    }

}
