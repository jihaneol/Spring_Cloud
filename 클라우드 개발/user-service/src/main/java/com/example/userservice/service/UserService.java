package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    ResponseUser getUserById(String userId);

    List<ResponseUser> getUserByAll();


    boolean deleteUser(String userId);

    ResponseUser updateUser(String userId, RequestUser user);
}
