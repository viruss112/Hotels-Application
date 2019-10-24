package com.example.project.Hotel;

import com.example.project.Mail.Mail;
import com.example.project.User.User;
import com.example.project.User.UserDTO;
import com.example.project.User.UserRepository;
import com.maxmind.geoip2.DatabaseReader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.*;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    private DatabaseReader dbReader;

    @Autowired
    public HotelService(HotelRepository hotelRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public HotelDTO saveHotel(HotelDTO hotelDTO) {

        Hotel hotel = new Hotel();
        modelMapper.map(hotelDTO, hotel);
        Hotel savedHotel = hotelRepository.save(hotel);
        HotelDTO hotelDTO1 = new HotelDTO();
        modelMapper.map(savedHotel, hotelDTO1);
        return hotelDTO1;
    }

    public HotelDTO getHotel(Integer hotelId) {

        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        HotelDTO hotelDTO = new HotelDTO();
        modelMapper.map(hotel.get(), hotelDTO);
        return hotelDTO;

    }

    public List<HotelDTO> getAllHotels() {

        List<Hotel> hotelList = (List<Hotel>) hotelRepository.findAll();
        List<HotelDTO> hotelDTOList = new ArrayList<>();
        for (Hotel hotel : hotelList) {
            HotelDTO hotelDTO = new HotelDTO();
            modelMapper.map(hotel, hotelDTO);
            hotelDTOList.add(hotelDTO);
        }
        return hotelDTOList;


    }

    public List<HotelDTO> getHotelsByCoordinates(CoordinatesDTO coordinatesDTO) {

        List<Hotel> hotelList = (List<Hotel>) hotelRepository.findAll();
        List<Hotel> hotelsInRadius = new ArrayList<>();
        List<HotelDTO> hotelDTOList = new ArrayList<>();
        double userLatitudeInMeters = 111132.92 - 559.82 * cos(2 * coordinatesDTO.getLatitude()) + 1.175 * cos(4 * coordinatesDTO.getLatitude()) - 0.0023 * cos(6 * coordinatesDTO.getLatitude());
        double userLongitudeInMeters = 111412.84 * cos(coordinatesDTO.getLongitude()) - 93.5 * cos(3 * coordinatesDTO.getLongitude()) + 0.118 * cos(5 * coordinatesDTO.getLongitude());

        for (Hotel hotel : hotelList) {
            double hotelLatitudeInMeters = 111132.92 - 559.82 * cos(2 * hotel.getLatitude()) + 1.175 * cos(4 * hotel.getLatitude()) - 0.0023 * cos(6 * hotel.getLatitude());
            double hotelLongitudeInMeters = 111412.84 * cos(hotel.getLongitude()) - 93.5 * cos(3 * hotel.getLongitude()) + 0.118 * cos(5 * hotel.getLongitude());

            double distance = sqrt(pow(userLatitudeInMeters - hotelLatitudeInMeters, 2) + pow(userLongitudeInMeters - hotelLongitudeInMeters, 2));
            System.out.println(distance);
            if (distance <= coordinatesDTO.getRadius() * 1000) {
                hotelsInRadius.add(hotel);
            }

        }

        for (Hotel hotel : hotelsInRadius) {

            HotelDTO hotelDTO = new HotelDTO();
            modelMapper.map(hotel, hotelDTO);
            hotelDTOList.add(hotelDTO);
        }

        List<HotelDTO> availableHotels = getAllHotelsWithAvailableRooms();
        List<HotelDTO> availableHotelsAndFound = new ArrayList<>();


        for (HotelDTO hotelDTO1 : availableHotels) {
            if (hotelDTOList.contains(hotelDTO1))
                availableHotelsAndFound.add(hotelDTO1);
        }


        return availableHotelsAndFound;
    }

    public HotelDTO updateRooms(HotelDTO hotelDTO, Integer hotelId, Integer userId) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        hotel.get().setSingleRooms(hotelDTO.getSingleRooms());
        hotel.get().setDoubleRooms(hotelDTO.getDoubleRooms());
        hotel.get().setSuiteRooms(hotelDTO.getSuiteRooms());

        HotelDTO hotelDTO1 = new HotelDTO();
        Hotel savedHotel = hotelRepository.save(hotel.get());
        modelMapper.map(savedHotel, hotelDTO1);
        Optional<User> user = userRepository.findById(userId);
        Mail.sendEmail(hotel, user.get());
        return hotelDTO1;

    }

    public List<HotelDTO> getAllHotelsWithAvailableRooms() {

        List<Hotel> hotelList = hotelRepository.getAllHotelsWithAvailableRooms();
        List<HotelDTO> hotelDTOList = new ArrayList<>();

        for (Hotel hotel : hotelList) {
            HotelDTO hotelDTO = new HotelDTO();
            modelMapper.map(hotel, hotelDTO);
            hotelDTOList.add(hotelDTO);
        }
        return hotelDTOList;
    }
}
