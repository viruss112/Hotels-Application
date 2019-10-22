package com.example.project.Hotel;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "hotel")
@Data
public class Hotel {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "hotel_name")
    private String hotelName;

    @Column(name = "single_rooms")
    private Integer singleRooms;

    @Column(name = "double_rooms")
    private Integer doubleRooms;

    @Column(name = "suite_rooms")
    private Integer suiteRooms;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Double.compare(hotel.latitude, latitude) == 0 &&
                Double.compare(hotel.longitude, longitude) == 0 &&
                Objects.equals(hotelId, hotel.hotelId) &&
                Objects.equals(hotelName, hotel.hotelName) &&
                Objects.equals(singleRooms, hotel.singleRooms) &&
                Objects.equals(doubleRooms, hotel.doubleRooms) &&
                Objects.equals(suiteRooms, hotel.suiteRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, hotelName, singleRooms, doubleRooms, suiteRooms, latitude, longitude);
    }
}
