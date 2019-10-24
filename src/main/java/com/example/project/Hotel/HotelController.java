package com.example.project.Hotel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {


    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

//    @GetMapping("/get-all")
//    public List<HotelDTO> hotelDTOList() {
//        return hotelService.getAllHotels();
//    }

//    @GetMapping("/get/{hotelId}")
//    public HotelDTO getHotel(@PathVariable Integer hotelId) {
//        return hotelService.getHotel(hotelId);
//    }

    @PostMapping("/create")
    public HotelDTO saveHotel(@RequestBody HotelDTO hotelDTO) {
        return hotelService.saveHotel(hotelDTO);
    }

    @PostMapping("/get-hotels")
    public List<HotelDTO> getHotelsByCoordinates(@RequestBody CoordinatesDTO coordinatesDTO) {
        return hotelService.getHotelsByCoordinates(coordinatesDTO);
    }
//
//    @PatchMapping("/update-rooms/{hotelId}/{userId}")
//    public HotelDTO updateRooms(@RequestBody HotelDTO hotelDTO, @PathVariable Integer hotelId, @PathVariable Integer userId) throws Exception {
//        return hotelService.updateRooms(hotelDTO, hotelId, userId);
//    }

//   @PostMapping("/get-hotels-with-available-rooms")
//    public List<HotelDTO> getAllHotelsWithAvailableRooms(){
//
//        return  hotelService.getAllHotelsWithAvailableRooms();
//   }


}
