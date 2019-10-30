package com.example.project.Mail;

import com.example.project.Reservation.Reservation;
import com.example.project.User.User;
import com.example.project.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

@Component
public class Mail  {


    private static JavaMailSender javaMailSender ;


    @Autowired
    public Mail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public static <T> void sendEmail(T scope, User user) {
        SimpleMailMessage mail = new SimpleMailMessage();


        mail.setTo(user.getEmail());
        mail.setFrom("vld.muri96@yahoo.com");
        if(scope instanceof User){
            mail.setSubject("Registration email");
            mail.setText("Your acount was succesfull registered \n " +
                    "username:"+ user.getEmail()+"\n"+
//                    "password:"+ user.getPassword()+"\n"+
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

    public static void NotificationEmail(Reservation reservation){


        LocalDate localDate = LocalDate.now();
        LocalDate localDate1= reservation.getReservationFromDate().toLocalDate();
        if(Duration.between(localDate1.atStartOfDay(),localDate.atStartOfDay()).toDays()==1){
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(reservation.getUser().getEmail());
            mail.setFrom("vld.muri96@yahoo.com");
            mail.setSubject("Reservation");
            mail.setText("Hello "+reservation.getUser().getFirstName()+" tomorrow you'll have a reservation at hotel "+reservation.getHotel().getHotelName()+" from "+ reservation.getReservationFromDate()+ " to "+
                    reservation.getReservationToDate());

            javaMailSender.send(mail);
        }



    }

}
