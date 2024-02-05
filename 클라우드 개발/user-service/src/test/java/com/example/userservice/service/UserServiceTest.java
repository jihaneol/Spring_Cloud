package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImp userService;

    private static UserDto userDto;

    @BeforeEach
        // 테스트 실행 전 실행
    void setup(){
        System.err.println("=============================== setup start ==============================");
        userDto = new UserDto();
        userDto.setPwd("1234");
        userDto.setName("테스트");
        userDto.setEmail("naver");
        System.err.println("=============================== setup end ==============================");
    }

    @Test
    @DisplayName("회원 가입")
    void login() throws Exception {
        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(UserEntity.builder()
                        .userId("1")
                        .name(userDto.getName())
                        .email(userDto.getEmail())
                        .encryptedPwd(userDto.getEncryptedPwd())
                        .build());
//
//        when(userRepository.findById(1l))
//                .thenReturn(Optional.ofNullable(UserEntity.builder()
//                        .userId("1")
//                        .name(userDto.getName())
//                        .email(userDto.getEmail())
//                        .encryptedPwd(userDto.getEncryptedPwd())
//                        .build()));

        //given
        //when
        UserDto user = userService.createUser(userDto);

//        System.out.println(user.getName());
        //then
        verify(userRepository,times(1)).save(any());
//        verify(userRepository).findById(1l);

    }
}