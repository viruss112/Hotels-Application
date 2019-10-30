package com.example.project;

import com.example.project.Hotel.Hotel;
import com.example.project.Mail.Mail;
import com.example.project.Reservation.Reservation;
import com.example.project.Reservation.RoomType;
import com.example.project.User.User;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificatorThread extends Thread {


//    private ReservationRepository reservationRepository;
//
//    @Autowired
//    public NotificatorThread(ReservationRepository reservationRepository) {
//        this.reservationRepository = reservationRepository;
//    }

    public NotificatorThread() {

    }

    public List<Reservation> getRes() {
        List<Reservation> reservationList = new ArrayList<>();
        User user = new User("vlad.muresan96@yahoo.com");
        Hotel hotel = new Hotel("hotel");
        Reservation reservation = new Reservation(Date.valueOf("2019-10-29"), RoomType.SUITE, Date.valueOf("2020-10-10"), user, hotel);
        reservationList.add(reservation);
        return reservationList;
    }


    @Override
    public void run() {
        List<Reservation> reservationList = getRes();
        LocalDateTime localDateTime = LocalDateTime.now();

        if (localDateTime.getHour() == 15 && localDateTime.getMinute() == 0 && localDateTime.getSecond() == 0) {
            for (Reservation reservation : reservationList) {
                Mail.NotificationEmail(reservation);
            }


        }
    }
}
