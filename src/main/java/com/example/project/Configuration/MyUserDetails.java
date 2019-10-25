package com.example.project.Configuration;

import com.example.project.User.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private String email;
    private String password;
    private boolean isActive;
    private List<GrantedAuthority> grantedAuthorityList;


    public MyUserDetails(UserDTO userDTO) {
        this.email=userDTO.getEmail();
        this.password=userDTO.getPassword();
        this.isActive=userDTO.isActive();
        this.grantedAuthorityList= Arrays.stream(userDTO.getRoles().split(","))
                                            .map(SimpleGrantedAuthority::new)
                                            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
