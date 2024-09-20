package com.nkm.mypracticespring.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String name;

    private String password;

    private String email;

}
