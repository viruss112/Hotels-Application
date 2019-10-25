package com.example.project.Reservation;

import com.example.project.Hotel.Hotel;
import com.example.project.Hotel.HotelRepository;
import com.example.project.Mail.Mail;
import com.example.project.User.User;
import com.example.project.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class ReservationService {

    private final ModelMapper modelMapper;
    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private  final UserRepository userRepository;


    @Autowired
    public ReservationService(ModelMapper modelMapper, ReservationRepository reservationRepository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;

    }
    @Transactional(rollbackOn = Exception.class)
    public ReservationDTO saveReservation(Integer userId,ReservationDTO reservationDTO,Integer hotelId) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        Reservation reservation = new Reservation();
        Optional<User> user = userRepository.findById(userId);
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        modelMapper.map(reservationDTO,reservation);
        reservation.setUser(user.get());
        reservation.setHotel(hotel.get());
        Reservation savedReservation=reservationRepository.save(reservation);
        ReservationDTO reservationDTO1 = new ReservationDTO();
        modelMapper.map(savedReservation,reservationDTO1);

        String roomType= String.valueOf(reservationDTO.getRoomType());

        switch (roomType){
            case "SUITE":hotel.get().setSuiteRooms(hotel.get().getSuiteRooms()-1);
            case "SINGLE":hotel.get().setSingleRooms(hotel.get().getSingleRooms()-1);
            case "DOUBLE" :hotel.get().setDoubleRooms(hotel.get().getDoubleRooms()-1);
        }

        hotelRepository.save(hotel.get());
        Mail.sendEmail(reservation,user.get());


        return reservationDTO1;

    }
    @Transactional(rollbackOn = Exception.class)
    public ReservationDTO deleteReservation(Integer userId){

        Reservation reservation = reservationRepository.getReservationsByUserId(userId);
        reservationRepository.delete(reservation);

        String roomType= String.valueOf(reservation.getRoomType());
        Hotel hotel = reservation.getHotel();

        switch (roomType){
            case "SUITE":hotel.setSuiteRooms(hotel.getSuiteRooms()+1);
            case "SINGLE":hotel.setSingleRooms(hotel.getSingleRooms()+1);
            case "DOUBLE" :hotel.setDoubleRooms(hotel.getDoubleRooms()+1);
        }

        hotelRepository.save(hotel);

        ReservationDTO reservationDTO = new ReservationDTO();
        modelMapper.map(reservation,reservationDTO);
        return reservationDTO;

    }
    @Transactional(rollbackOn = Exception.class)
    public ReservationDTO updateDates(Integer userId, ReservationDTO reservationDTO) {

        Reservation reservation = reservationRepository.getReservationsByUserId(userId);
        reservation.setReservationFromDate(reservationDTO.getReservationFromDate());
        reservation.setReservationToDate(reservationDTO.getReservationToDate());
        Reservation savedReservation =reservationRepository.save(reservation);
        ReservationDTO reservationDTO1 = new ReservationDTO();
        modelMapper.map(savedReservation,reservationDTO1);
        return  reservationDTO1;

    }
}
