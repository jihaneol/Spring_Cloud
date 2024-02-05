package com.example.userservice.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import static org.assertj.core.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    private static UserEntity user;

    @BeforeEach
        // 테스트 실행 전 실행
    void setup(){
        System.err.println("=============================== setup ==============================");
        user=user.builder()
                .userId(UUID.randomUUID().toString())
                .name("지")
                .email("sdf")
                .encryptedPwd("sdf")
                .build();

    }
    @Test
    @DisplayName("회원가입")
    @Transactional
    @Order(1)
    void test() throws Exception {
        //given
        // user 사용

        //when
        UserEntity save = userRepository.save(user);


        //then
        assertThat(save.getName()).isEqualTo("지");
    }

}