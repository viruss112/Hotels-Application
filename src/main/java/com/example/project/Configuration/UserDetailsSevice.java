package com.example.project.Configuration;

import com.example.project.User.User;
import com.example.project.User.UserDTO;
import com.example.project.User.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailsSevice implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsSevice(UserService userService) {
        this.userService = userService;

    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {


        Optional<UserDTO> userDTO = null;
        try {
            userDTO = Optional.ofNullable(userService.getUserByUserName(userEmail));
        } catch (Exception e) {
            userDTO.orElseThrow(()-> new UsernameNotFoundException("Not found!"+userEmail));
        }


        return userDTO.map(MyUserDetails::new).get();
    }
}
