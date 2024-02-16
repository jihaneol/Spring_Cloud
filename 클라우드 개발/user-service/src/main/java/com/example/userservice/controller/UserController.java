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

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final Greeting greeting;
    private final Environment env;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/health_check")
    public String status() {

        return String.format("It's Working in User Service"
               + ", port(local.server.port) ="+ env.getProperty("local.server.port")
               + ", port(server.port) ="+ env.getProperty("server.port")
               + ", token secret ="+ env.getProperty("token.secret")
               + ", token expiration time"+ env.getProperty("token.expiration_time")
        );
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    // 회원 가입
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto user1 = userService.createUser(userDto);


        ResponseUser responseUser = modelMapper.map(user1, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
    // 회원 리스트
    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByAll());
    }
    // 특정 회원 정보
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
    }

    // 회원 정보 삭제
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> deleteUser(@PathVariable("userId") String userId) {
        if(userService.deleteUser(userId)==1){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    // 회원 정보 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> updateUser(@PathVariable("userId") String userId,
                                                   @RequestBody RequestUser user) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, user));
    }




}
