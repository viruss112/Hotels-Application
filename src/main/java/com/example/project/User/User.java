package com.example.project.User;

import com.example.project.Reservation.Reservation;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;

    @OneToMany(mappedBy = "user" ,fetch = FetchType.LAZY)
    private List<Reservation> reservationList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(token, user.token) &&
                Objects.equals(reservationList, user.reservationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, password, token, reservationList);
    }
}
