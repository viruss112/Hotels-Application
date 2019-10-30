package com.example.project.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserDTO saveUser(@RequestBody UserDTO userDTO) throws Exception {
        return userService.saveUser(userDTO);
    }

    @GetMapping("/get/{userId}")
    public UserDTO getUser(@PathVariable Integer userId) throws Exception {
        return userService.getUser(userId);
    }

    @GetMapping("/get-all")
    public List<UserDTO> getAll() throws Exception {
        return userService.getAll();
    }

}
