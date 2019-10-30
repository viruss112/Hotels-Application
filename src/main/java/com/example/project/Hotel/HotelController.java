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

    @GetMapping("/get-all")
    public List<HotelDTO> hotelDTOList() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/get/{hotelId}")
    public HotelDTO getHotel(@PathVariable Integer hotelId) {
        return hotelService.getHotel(hotelId);
    }

    @PostMapping("/create")
    public HotelDTO saveHotel(@RequestBody HotelDTO hotelDTO) {
        return hotelService.saveHotel(hotelDTO);
    }

    @PostMapping("/get-hotels")
    public List<HotelDTO> getHotelsByCoordinates(@RequestBody CoordinatesDTO coordinatesDTO) {
        return hotelService.getHotelsByCoordinates(coordinatesDTO);
    }

    @PostMapping("/get-hotels-by-region")
    public List<HotelDTO> getHotelsByRegion(@RequestBody RegionDTO regionDTO) throws Exception {
        return hotelService.getHotelsByRegion(regionDTO);
    }




}
