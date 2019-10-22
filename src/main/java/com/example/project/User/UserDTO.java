package com.example.project.User;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Data
public class UserDTO {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(userId, userDTO.userId) &&
                Objects.equals(firstName, userDTO.firstName) &&
                Objects.equals(lastName, userDTO.lastName) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(token, userDTO.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, password, token);
    }
}
