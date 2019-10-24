package com.example.project.Reservation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation,Integer> {

    @Query(value = "select * from reservation where user_id=:userId",nativeQuery = true)
    Reservation getReservationsByUserId(@Param("userId") Integer userId);
}
