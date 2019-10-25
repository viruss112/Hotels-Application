package com.example.project.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservation")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create/{userId}/{hotelId}")
    public ReservationDTO saveReservation (@PathVariable Integer userId, @RequestBody ReservationDTO reservationDTO, @PathVariable Integer hotelId) throws Exception{
        return reservationService.saveReservation(userId,reservationDTO,hotelId);
    }

    @DeleteMapping("/delete/{userId}")
    public ReservationDTO deleteReservation(@PathVariable Integer userId){
        return reservationService.deleteReservation(userId);
    }

    @PatchMapping("/update-dates/{userId}")
    public ReservationDTO updateDates(@PathVariable Integer userId,@RequestBody ReservationDTO reservationDTO){
        return reservationService.updateDates(userId,reservationDTO);
    }

}
