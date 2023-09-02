package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        UserEntity userEntity = UserEntity.builder()
                .email(userDto.getEmail())
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .encryptedPwd(passwordEncoder.encode(userDto.getPwd()))
                .build();
        userRepository.save(userEntity);

        return userDto;
    }
}
