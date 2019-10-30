package com.example.project.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private  final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public RoleDTO saveRole( @RequestBody  RoleDTO roleDTO){
        return roleService.saveRole(roleDTO);
    }

    @DeleteMapping("/delete-role/{userId}")
    public RoleDTO deleteRole(@PathVariable Integer userId){
        return roleService.deleteRole(userId);
    }
}
