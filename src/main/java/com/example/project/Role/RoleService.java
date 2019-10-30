package com.example.project.Role;

import com.example.project.User.User;
import com.example.project.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RoleService {

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleService(ModelMapper modelMapper, RoleRepository roleRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public RoleDTO saveRole(RoleDTO roleDTO) {

        Role role = new Role();
        modelMapper.map(roleDTO, role);
        Optional<User> user = userRepository.findById(roleDTO.getUserId());
        role.setUser(user.get());
        Role savedRole = roleRepository.save(role);
        RoleDTO roleDTO1 = new RoleDTO();
        modelMapper.map(savedRole, roleDTO1);
        return roleDTO1;

    }

    @Transactional(rollbackOn = Exception.class)
    public RoleDTO deleteRole(Integer userId) {

        Role role = roleRepository.getRoleByUserId(userId);
        RoleDTO roleDTO = new RoleDTO();
        modelMapper.map(role, roleDTO);
        roleRepository.delete(role);
        return roleDTO;
    }
}
