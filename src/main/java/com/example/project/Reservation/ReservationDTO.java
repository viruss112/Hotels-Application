package com.example.project.Reservation;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Objects;

@Data
@Component
public class ReservationDTO {

    private Integer reservationId;
    private Date reservationToDate;
    private Date reservationFromDate;
    private Integer userId;
    private Integer hotelId;
    private RoomType roomType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationDTO that = (ReservationDTO) o;
        return Objects.equals(reservationId, that.reservationId) &&
                Objects.equals(reservationToDate, that.reservationToDate) &&
                Objects.equals(reservationFromDate, that.reservationFromDate) &&
                Objects.equals(userId, that.userId) &&
                roomType == that.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, reservationToDate, reservationFromDate, userId, roomType);
    }
}
