package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {

    private final Greeting greeting;
    private final Environment env;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/health_check")
    public String status() {

        return String.format("It's Working in User Service "
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", token secret =" + env.getProperty("token.secret"));



    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto user1 = userService.createUser(userDto);


        ResponseUser responseUser = new ResponseUser(userDto.getEmail(), userDto.getName(), userDto.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }


}
