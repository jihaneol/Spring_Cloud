package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        log.info(userDto.toString());
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userRepository.save(userEntity);

        return userDto;
    }

    @Override
    public ResponseUser getUserById(String userId) {
        log.info(userId);
        UserDto userDto = userRepository.findByUserId(userId)
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));

        userDto.setOrders(new ArrayList<>());

        return modelMapper.map(userDto, ResponseUser.class);
    }

    @Override
    public List<ResponseUser> getUserByAll() {
        Iterable<UserEntity> all = userRepository.findAll();

        List<ResponseUser> responseUserList = new ArrayList<>();

        all.forEach(v-> responseUserList.add(modelMapper.map(v, ResponseUser.class)));

        return responseUserList;
    }
}
