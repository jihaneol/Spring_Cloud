package com.example.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseUser  {
    private String email;
    private String name;
    private String userId;
}
