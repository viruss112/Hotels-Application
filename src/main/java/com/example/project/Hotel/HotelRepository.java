package com.example.project.Hotel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends CrudRepository<Hotel,Integer> {

    @Query(value = "select * from hotel where suite_rooms <> 0 or double_rooms <> 0 or single_rooms <> 0",nativeQuery = true)
    List<Hotel> getAllHotelsWithAvailableRooms();

}
