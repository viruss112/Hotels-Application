package com.example.project.Hotel;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Data
public class HotelDTO {

    private Integer hotelId;
    private  String hotelName;
    private double latitude;
    private double longitude;
    private Integer singleRooms;
    private Integer doubleRooms;
    private Integer suiteRooms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelDTO hotelDTO = (HotelDTO) o;
        return Double.compare(hotelDTO.latitude, latitude) == 0 &&
                Double.compare(hotelDTO.longitude, longitude) == 0 &&
                Objects.equals(hotelId, hotelDTO.hotelId) &&
                Objects.equals(hotelName, hotelDTO.hotelName) &&
                Objects.equals(singleRooms, hotelDTO.singleRooms) &&
                Objects.equals(doubleRooms, hotelDTO.doubleRooms) &&
                Objects.equals(suiteRooms, hotelDTO.suiteRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, hotelName, latitude, longitude, singleRooms, doubleRooms, suiteRooms);
    }
}
