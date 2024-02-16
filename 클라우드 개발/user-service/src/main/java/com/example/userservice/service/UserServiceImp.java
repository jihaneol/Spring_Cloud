package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }


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

        all.forEach(v -> responseUserList.add(modelMapper.map(v, ResponseUser.class)));

        return responseUserList;
    }

    @Transactional
    @Override
    public Integer deleteUser(String userId) {

        return userRepository.deleteByUserId(userId);
    }
    @Transactional
    @Override
    public ResponseUser updateUser(String userId, RequestUser user) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException(userId));

        userEntity.update(user);


        return modelMapper.map(userEntity,ResponseUser.class);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }


}
