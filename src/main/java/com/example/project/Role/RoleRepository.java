package com.example.project.Role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

    @Query(value = "select * from roles where user_id=:userId",nativeQuery = true)
    Role getRoleByUserId(@Param("userId") Integer userId);
}
