package com.example.userservice.vo;

import lombok.Data;
import lombok.Getter;

@Data
public class RequestUser {

    private String email;
    private String name;
    private String pwd;
}
