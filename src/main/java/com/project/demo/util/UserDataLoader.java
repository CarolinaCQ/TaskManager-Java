package com.project.demo.util;

import com.project.demo.dto.RoleDto;
import com.project.demo.dto.UserPostDto;
import com.project.demo.service.IRoleService;
import com.project.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataLoader implements CommandLineRunner {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    public void loadUserData(){

        if(roleService.getAllRoles().isEmpty()){
            RoleDto roleAdmin = new RoleDto("ROLE_ADMIN");
            roleService.loadRoleData(roleAdmin);
            RoleDto roleUser = new RoleDto("ROLE_USER");
            roleService.loadRoleData(roleUser);
        }

        if(userService.getAllUsers().isEmpty()){
            UserPostDto userAdmin = new UserPostDto("caroq", "1234");
            userService.loadUserData(userAdmin);
        }
    }
}
