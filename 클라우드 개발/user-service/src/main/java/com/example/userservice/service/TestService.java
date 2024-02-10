package com.example.userservice.service;


import com.example.userservice.repository.TestEntity;
import com.example.userservice.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    public void test() {
        TestEntity testEntity = TestEntity.makeTest(11L, 12, 23, LocalDateTime.now(),"sdf");

        testRepository.save(testEntity);

    }
}
