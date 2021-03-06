package com.example.project.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "select * from user where email=:email",nativeQuery = true)
    User getUserByEmail(@Param("email") String email);

}
