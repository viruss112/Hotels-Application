package com.example.project.Role;

import com.example.project.User.User;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_given")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


}
