package com.example.project.Role;

import com.example.project.Reservation.RoomType;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class RoleDTO {

    private Integer roleId;
    private Integer userId;
    private RoleType roleType;
}
