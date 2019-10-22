package com.example.project.User;

import com.example.project.EncryptionUtil.EncryptionUtil;
import com.example.project.Hotel.HotelService;
import com.example.project.Mail.Mail;
import com.example.project.Utilities.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    private final EncryptionUtil encryptionUtil;
    private final Mail mail;



    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, EncryptionUtil encryptionUtil, HotelService hotelService, Mail mail) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encryptionUtil = encryptionUtil;
        this.mail = mail;
    }

    @Transactional(rollbackOn = Exception.class)
    public UserDTO saveUser(UserDTO userDTO) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {

        User user = new User();
        modelMapper.map(userDTO, user);
        validate(user);
        user.setPassword(encryptionUtil.encrypt(userDTO.getPassword()));
        user.setEmail(encryptionUtil.encrypt(userDTO.getEmail()));
        UUID uuid = UUID.randomUUID();
        user.setToken(encryptionUtil.encrypt(uuid.toString()));
        User savedUser = userRepository.save(user);
        UserDTO userDTO1 = new UserDTO();
        modelMapper.map(savedUser, userDTO1);
        mail.sendEmail(savedUser);
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

    public UserDTO getUser(Integer userId) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        Optional<User> user = userRepository.findById(userId);
        UserDTO userDTO = new UserDTO();
        modelMapper.map(user.get(), userDTO);
        userDTO.setEmail(encryptionUtil.decrypt(user.get().getEmail()));
        userDTO.setPassword(encryptionUtil.decrypt(user.get().getPassword()));
        userDTO.setToken(encryptionUtil.decrypt(user.get().getToken()));

        return userDTO;

    }

    public List<UserDTO> getAll() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        List<User> userList = (List<User>) userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = new UserDTO();
            modelMapper.map(user, userDTO);
            userDTO.setEmail(encryptionUtil.decrypt(user.getEmail()));
            userDTO.setPassword(encryptionUtil.decrypt(user.getPassword()));
            userDTO.setToken(encryptionUtil.decrypt(user.getToken()));
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public UserDTO login(UserDTO userDTO) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {

        String email= encryptionUtil.encrypt(userDTO.getEmail());
        String password = encryptionUtil.encrypt(userDTO.getPassword());

        User user = userRepository.getUserByCredentials(email, password);
        UserDTO userDTO1 = new UserDTO();
        modelMapper.map(user, userDTO1);
        userDTO1.setEmail(encryptionUtil.decrypt(user.getEmail()));
        userDTO1.setPassword(encryptionUtil.decrypt(user.getPassword()));
        userDTO1.setToken(encryptionUtil.decrypt(user.getToken()));
        return userDTO1;


    }



}
