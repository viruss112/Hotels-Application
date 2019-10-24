package com.example.project.Reservation;

import com.example.project.Hotel.Hotel;
import com.example.project.User.User;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity

@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name =" reservationId")
    private Integer reservationId;

    @Column(name = "reservation_from_date")
    private Date reservationFromDate;

    @Column(name = "room_type")
    private RoomType roomType;

    @Column(name = "reservation_to_date")
    private Date reservationToDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id",nullable = false)
    private Hotel hotel;

}
