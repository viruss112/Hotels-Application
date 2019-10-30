package com.example.project.User;

import com.example.project.Mail.Mail;
import com.example.project.Utilities.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.project.ValidationUtil.ValidationUtils.validateRangeLength;
import static com.example.project.ValidationUtil.ValidationUtils.validateRequiredObject;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Mail mail;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, Mail mail, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.mail = mail;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackOn = Exception.class)
    public UserDTO saveUser(UserDTO userDTO)  {

        User user = new User();
        modelMapper.map(userDTO, user);
        validate(user);
        user.setEmail(userDTO.getEmail());
        UUID uuid = UUID.randomUUID();
        user.setToken(uuid.toString());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(user);
        UserDTO userDTO1 = new UserDTO();
        modelMapper.map(savedUser, userDTO1);
       // mail.sendEmail(user,savedUser);
        return userDTO1;

    }

    private void validate(User user) {


        validateRequiredObject(user.getEmail(), "email", 45);

        if (!Utilities.isEmptyOrNull(user.getPassword())) {
            validateRangeLength(user.getPassword(), "password", 8, 50);
        }

        validateRequiredObject(user.getEmail(), "email", 45);
        validateRequiredObject(user.getFirstName(), "firstName", 45);
        validateRequiredObject(user.getLastName(), "lastName", 45);


    }

    public UserDTO getUser(Integer userId) {

        Optional<User> user = userRepository.findById(userId);
        UserDTO userDTO = new UserDTO();
        modelMapper.map(user.get(), userDTO);
        return userDTO;

    }

    public List<UserDTO> getAll() {

        List<User> userList = (List<User>) userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = new UserDTO();
            modelMapper.map(user, userDTO);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }


    public User getUserByUserName(String userEmail) {
            User user = userRepository.getUserByEmail(userEmail);
            return user;

    }





}
